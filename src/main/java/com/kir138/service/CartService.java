package com.kir138.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.mapper.CartMapper;
import com.kir138.model.dto.CartDto;
import com.kir138.model.dto.CartItemEvent;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.model.entity.OutboxEvent;
import com.kir138.model.entity.OutboxStatus;
import com.kir138.repository.CartRepository;
import com.kir138.repository.OutboxEventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    //Реализуйте методы для создания, получения, обновления и удаления корзин.
    //Добавьте методы для добавления и удаления элементов корзины.
    //Добавьте логику установки значений полей createdAt, updatedAt
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ObjectMapper objectMapper;
    private final OutboxEventRepository outboxEventRepository;

    public CartDto getCartById(Long id) {
        return cartRepository.findById(id)
                .stream()
                .map(cartMapper::toMapper)
                .findFirst()
                .orElseThrow();
    }

    //добавить предмет(CartItem в корзину Cart)
    @Transactional
    public void addItemToCart(Long cartId, Long productId, Integer quantity, Long userId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .id(cartId)
                            .userId(userId)
                            .updatedAt(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())
                            .items(new ArrayList<>())
                            .build();
                    return cartRepository.save(newCart);
                });

        // Формируем событие, которое должно быть отправлено в Kafka.
        CartItemEvent event = CartItemEvent.builder()
                .cartId(cartId)
                .productId(productId)
                .quantity(quantity)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            String payload = objectMapper.writeValueAsString(event);
            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType("Cart")
                    .aggregateId(cart.getId())
                    .type("CartItemRequest")
                    .topic("cart-item-added")  // название топика, куда мы хотим отправить сообщение
                    .payload(payload)
                    .status(OutboxStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .build();

            saveOutboxEvent(outboxEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка сериализации события CartItemEvent", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OutboxEvent saveOutboxEvent(OutboxEvent event) {
        return outboxEventRepository.save(event);
    }

    public List<CartDto> getAllCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toMapper)
                .toList();
    }

    @Transactional
    public CartDto saveOrUpdateCart(Cart cart) {
        Cart savedCart = cartRepository.findById(cart.getId())
                .map(c -> {
                    c.setUserId(cart.getUserId());
                    c.setItems(cart.getItems());
                    c.setUpdatedAt(LocalDateTime.now());
                    return cartRepository.save(c);
                })
                .orElseGet(() -> {
                    return cartRepository.save(Cart.builder()
                            .userId(cart.getUserId())
                            .items(cart.getItems())
                            .createdAt(LocalDateTime.now())
                            .build());

                });
        return cartMapper.toMapper(savedCart);
    }

    @Transactional
    public CartDto removeItemFromCart(CartItem item, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().removeIf(i -> i.getId().equals(item.getId()));
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.delete(cart);
        return cartMapper.toMapper(cart);
    }

    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }
}
