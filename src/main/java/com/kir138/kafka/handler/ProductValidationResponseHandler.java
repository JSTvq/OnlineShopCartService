package com.kir138.kafka.handler;

import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.repository.CartItemRepository;
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
        System.out.println("влючается метод хендлера корзины");
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
                        System.out.println("Creating new cart: " + newCart);
                        return cartRepository.save(newCart);
                    });

            System.out.println("Колвоооооооооооооооооооооооооо " + response.getProductId());
            System.out.println("Колвоооооооооооооооооооооооооо " + response.getQuantity());

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
