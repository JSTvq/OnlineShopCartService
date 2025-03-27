package com.kir138.controller;

import com.kir138.model.dto.CartDto;
import com.kir138.model.entity.Cart;
import com.kir138.model.entity.CartItem;
import com.kir138.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CartController {

    private final CartService cartService;

    @GetMapping("/{id}")
    public CartDto getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PutMapping("/{cartId}/add-item")
    public ResponseEntity<String> addItemToCart(@PathVariable Long cartId,
                                                @RequestParam Long productId,
                                                @RequestParam Integer quantity,
                                                @RequestParam Long userId) {
        cartService.addItemToCart(cartId, productId, quantity, userId);
        return ResponseEntity.ok("Item addition request sent to Kafka.");
    }

    @PostMapping("/saveCart")
    public CartDto saveOrUpdateCart(@RequestBody Cart cart) {
        return cartService.saveOrUpdateCart(cart);
    }

    @DeleteMapping("/{id}")
    public CartDto removeItemFromCart(@RequestBody CartItem item, @PathVariable Long id) {
        return cartService.removeItemFromCart(item, id);
    }
}
