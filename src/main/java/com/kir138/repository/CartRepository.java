package com.kir138.repository;

import com.kir138.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Collection<Object> getCartById(Long id);

    Optional<Cart> findByUserId(Long userId);
}
