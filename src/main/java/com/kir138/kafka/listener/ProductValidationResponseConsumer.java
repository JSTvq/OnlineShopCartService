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
    public void listenProductValidationResponse(ConsumerRecord<String,
                                                            ProductValidationResponse> consumerRecord) {
        /*public void listenProductValidationResponse(ConsumerRecord<String,
                ProductValidationResponse> consumerRecord,
                Acknowledgment ack) {*/
        System.out.println("включается метод прослушки");
        try {
            ProductValidationResponse response = consumerRecord.value();
            productValidationResponseHandler.handler(response);
            //ack.acknowledge();
            System.out.println("метод успешно завершился");
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ProductValidationResponse", e);
        }
    }
}
