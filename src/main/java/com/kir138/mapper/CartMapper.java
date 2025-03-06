package com.kir138.mapper;

import com.kir138.model.dto.CartDto;
import com.kir138.model.dto.CartItemDto;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

/*@Component
public class CartMapper {

    public CartDto toMapper(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }
}*/

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
