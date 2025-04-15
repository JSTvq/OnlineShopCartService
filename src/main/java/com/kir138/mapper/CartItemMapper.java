package com.kir138.mapper;

import com.kir138.model.dto.CartItemDto;
import com.kir138.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toMapper(CartItem cartItem);

    CartItem toMapper(CartItemDto cartItemDto);
}

/*
@Component
public class CartItemMapper {

    public CartItemDto toMapper(CartItem cartItem) {
        return CartItemDto.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemDto cartItemDto) {
        return CartItem.builder()
                .id(cartItemDto.getId())
                .productId(cartItemDto.getProductId())
                .quantity(cartItemDto.getQuantity())
                .build();
    }
}
*/
