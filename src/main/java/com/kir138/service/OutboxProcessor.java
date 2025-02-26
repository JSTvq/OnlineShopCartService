package com.kir138.service;

import com.kir138.model.dto.ProductValidationResponse;
import com.kir138.model.entity.OutboxEvent;
import com.kir138.enumStatus.OutboxStatus;
import com.kir138.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, ProductValidationResponse> kafkaTemplate;

    @Scheduled(fixedDelay = 5000)
    public void processPendingEvents() {
        List<OutboxEvent> events = outboxEventRepository.findAllByStatus(OutboxStatus.PENDING);
        for (OutboxEvent event : events) {
            try {
                // Отправка в Kafka
                kafkaTemplate.send(event.getTopic(), event.getPayload());
                // Если отправка прошла успешно, меняем статус на SENT.
                event.setStatus(OutboxStatus.SENT);
                outboxEventRepository.save(event);
            } catch (Exception ex) {
                event.setStatus(OutboxStatus.FAILED);
                outboxEventRepository.save(event);
            }
        }
    }
}
