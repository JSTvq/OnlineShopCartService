package com.kir138.mapper;

import com.kir138.model.dto.CartDto;
import com.kir138.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = CartItemMapper.class
)
public interface CartMapper {

    CartDto toMapper(Cart cart);

    Cart toEntity(CartDto cartDto);

    List<CartDto> toMapper(List<Cart> carts);

    @Mapping(target = "version", ignore = true)
    List<Cart> toEntity(List<CartDto> cartDtos);
}
