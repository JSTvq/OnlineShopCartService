package com.kir138.kafka.handler;

import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductValidationResponseHandler {

    private final CartRepository cartRepository;

    @Transactional
    public void handler(ProductValidationResponse response) {
        System.out.println("включается метод хендлера корзины");
        try {
            if (!response.getIsValid()) {
                System.out.println("Product with ID " + response.getProductId() + " is not available!");
                return;
            }

            Cart cart = cartRepository.findById(response.getCartId()).orElseThrow();

            System.out.println("Кол-во " + response.getProductId());
            System.out.println("Кол-во " + response.getQuantity());

            CartItem cartItem = CartItem.builder()
                    .productId(response.getProductId())
                    .quantity(response.getQuantity())
                    .build();

            cartItem.setCart(cart);
            cart.getItems().add(cartItem);

            System.out.println("Adding cart item: " + cartItem + " to cart: " + cart);

            cartRepository.save(cart);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ProductValidationResponse", e);
        }
    }
}
