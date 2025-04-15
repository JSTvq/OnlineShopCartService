package com.kir138.model.dto;

import com.kir138.model.entity.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;

    private Long userId;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<CartItemDto> items = new ArrayList<>();

    public List<CartItemDto> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartDto cartDto = (CartDto) o;
        return Objects.equals(id, cartDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
