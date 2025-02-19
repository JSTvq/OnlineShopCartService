package com.kir138.service;

import com.kir138.model.dto.CartItemEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
//@Service
/*public class KafkaProducer {
    private final KafkaTemplate<String, CartItemEvent> kafkaTemplate;

    public void sendCartItemEvent(CartItemEvent cartItemEvent) {
        kafkaTemplate.send("cart-item-added", cartItemEvent);
    }
}*/
