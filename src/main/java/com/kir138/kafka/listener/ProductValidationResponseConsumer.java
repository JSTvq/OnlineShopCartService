package com.kir138.kafka.listener;

import com.kir138.kafka.handler.ProductValidationResponseHandler;
import com.kir138.model.dto.CartItemEvent;
import com.kir138.model.dto.ProductValidationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductValidationResponseConsumer {
    private final ProductValidationResponseHandler productValidationResponseHandler;

    @KafkaListener(topics = "product-validation-response", groupId = "online-shop-group")
    @Transactional
    public void listenProductValidationResponse(ConsumerRecord<String, ProductValidationResponse> consumerRecord,
                                                    Acknowledgment ack) {
        try {
            ProductValidationResponse response = consumerRecord.value();
            productValidationResponseHandler.handler(response);
            ack.acknowledge();
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ProductValidationResponse", e);
        }
    }
}
/**
 * public class ProductServiceConsumer {
 *     private final AddCartItemHandler addCartItemHandler;
 *
 *     @KafkaListener(topics = "cart-item-added", groupId = "online-shop-group")
 *     public void listenCartItemEvent(ConsumerRecord<String, CartItemEvent> consumerRecord,
 *                                     Acknowledgment ack) {
 *         try {
 *             CartItemEvent event = consumerRecord.value();
 *             addCartItemHandler.handle(event);
 *
 *             ack.acknowledge();
 *         } catch (RuntimeException e) {
 *             log.error(e.getMessage());
 *         }
 *     }
 * }
 */
