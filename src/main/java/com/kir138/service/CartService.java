package com.kir138.service;

import com.kir138.mapper.CartMapper;
import com.kir138.model.dto.CartDto;
import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.model.entity.OutboxEvent;
import com.kir138.enumStatus.OutboxStatus;
import com.kir138.repository.CartRepository;
import com.kir138.repository.OutboxEventRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private final OutboxEventRepository outboxEventRepository;

    public CartDto getCartById(Long id) {
        return cartRepository.findById(id)
                .stream()
                .map(cartMapper::toMapper)
                .findFirst()
                .orElseThrow();
    }

    //добавить предмет(CartItem в корзину Cart)
    public void addItemToCart(Long productId, Integer quantity, Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .userId(userId)
                                .items(new ArrayList<>())
                                .build()
                ));

        try {
            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .aggregateType("Cart")
                    .aggregateId(cart.getId())
                    .type("CartItemRequest")
                    .topic("cart-item-added")
                    .payload(ProductValidationResponse.builder()
                            .cartId(cart.getId())
                            .productId(productId)
                            .userId(userId)
                            .quantity(quantity)
                            .build())
                    .status(OutboxStatus.PENDING)
                    .build();

            outboxEventRepository.save(outboxEvent);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации события CartItemEvent", e);
        }
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
                    c.setUpdatedAt(cart.getUpdatedAt());
                    return cartRepository.save(c);
                })
                .orElseGet(() -> {
                    return cartRepository.save(Cart.builder()
                            .userId(cart.getUserId())
                            .items(cart.getItems())
                            .createdAt(cart.getCreatedAt())
                            .build());
                });
        return cartMapper.toMapper(savedCart);
    }

    public CartDto removeItemFromCart(CartItem item, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().removeIf(i -> i.getId().equals(item.getId()));
        cartRepository.delete(cart);
        return cartMapper.toMapper(cart);
    }

    public void deleteCartById(Long id) {
        cartRepository.deleteById(id);
    }
}
