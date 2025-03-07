package com.kir138.mapper;

import com.kir138.model.dto.CartItemDto;
import com.kir138.model.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toMapper(CartItem cartItem);

    CartItem toMapper(CartItemDto cartItemDto);
}
