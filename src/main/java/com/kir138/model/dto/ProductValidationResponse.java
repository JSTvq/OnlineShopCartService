package com.kir138.model.dto;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductValidationResponse {
    private Long cartId;
    private Long userId;
    private Long productId;
    private boolean isValid;
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductValidationResponse response = (ProductValidationResponse) o;
        return Objects.equals(cartId, response.cartId) && Objects.equals(userId, response.userId) && Objects.equals(productId, response.productId) && Objects.equals(isValid, response.isValid) && Objects.equals(quantity, response.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, userId, productId, isValid, quantity);
    }
}
