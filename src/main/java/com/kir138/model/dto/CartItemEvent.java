package com.kir138.model.dto;

import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemEvent {

    private Long cartId;

    private Long productId;

    private Integer quantity;

    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItemEvent event = (CartItemEvent) o;
        return Objects.equals(cartId, event.cartId) && Objects.equals
                (productId, event.productId) && Objects.equals
                (quantity, event.quantity) && Objects.equals(userId, event.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId, quantity, userId);
    }
}
