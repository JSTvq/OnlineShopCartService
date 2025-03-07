package com.kir138.repository;

import com.kir138.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Collection<Object> getCartById(Long id);
}
