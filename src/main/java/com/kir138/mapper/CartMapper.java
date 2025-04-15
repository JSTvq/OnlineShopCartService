package com.kir138.mapper;

import com.kir138.model.dto.CartDto;
import com.kir138.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = CartItemMapper.class
)
public interface CartMapper {

    @Mapping(target = "items", source = "items")
    CartDto toMapper(Cart cart);

    Cart toEntity(CartDto cartDto);

    List<CartDto> toMapper(List<Cart> carts);

    @Mapping(target = "version", ignore = true)
    List<Cart> toEntity(List<CartDto> cartDtos);
}
/*@Component
public class CartMapper {

    public CartDto toMapper(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .build();
    }

    public Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.getId())
                .userId(cartDto.getUserId())
                .build();
    }
}*/
