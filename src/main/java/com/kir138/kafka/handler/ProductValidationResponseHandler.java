package com.kir138.kafka.handler;

import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.repository.CartItemRepository;
import com.kir138.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductValidationResponseHandler {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void handler(ProductValidationResponse response) {
        try {
            if (!response.isValid()) {
                System.out.println("Product with ID " + response.getProductId() + " is not available!");
                return;
            }

            Cart cart = cartRepository.findById(response.getCartId())
                    .orElseGet(() -> {
                        Cart newCart = Cart.builder()
                                .userId(response.getUserId())
                                .items(new ArrayList<>())
                                .build();
                        return cartRepository.save(newCart);
                    });

            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .productId(response.getProductId())
                    .quantity(response.getQuantity())
                    .build();

            cart.getItems().add(cartItem);

            cartRepository.save(cart);
            cartItemRepository.save(cartItem);

        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ProductValidationResponse", e);
        }
    }
}
