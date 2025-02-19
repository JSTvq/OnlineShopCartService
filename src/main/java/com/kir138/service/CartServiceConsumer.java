package com.kir138.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.repository.CartItemRepository;
import com.kir138.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;


@RequiredArgsConstructor
@Service
public class CartServiceConsumer {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "product-validation-response", groupId = "online-shop-group")
    @Transactional
    public void listenProductValidationResponse(String message) {
        try {
            // Десериализуем входящее сообщение в объект ProductValidationResponse
            ProductValidationResponse response = objectMapper.readValue(message, ProductValidationResponse.class);
            if (!response.isValid()) {
                System.out.println("Product with ID " + response.getProductId() + " is not available!");
                return;
            }

            // Находим корзину или создаём новую
            Cart cart = cartRepository.findById(response.getCartId())
                    .orElseGet(() -> {
                        Cart newCart = Cart.builder()
                                .id(response.getCartId())
                                .userId(response.getUserId())
                                .items(new ArrayList<>())
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();
                        return cartRepository.save(newCart);
                    });

            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .productId(response.getProductId())
                    .quantity(response.getQuantity())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            cart.getItems().add(cartItem);
            cart.setUpdatedAt(LocalDateTime.now());

            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

            System.out.println("Product added to cart successfully!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ProductValidationResponse", e);
        }
    }
}
