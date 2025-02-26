package com.kir138.mapper;

import com.kir138.model.dto.CartDto;
import com.kir138.model.entity.Cart;
import org.springframework.stereotype.Component;

//@Mapper()
@Component
public class CartMapper {

    public CartDto toMapper(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }
}
