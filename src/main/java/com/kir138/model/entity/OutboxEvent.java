package com.kir138.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aggregateType; // Например, "Cart"
    private Long aggregateId;

    // Можно сохранить топик, в который должно быть отправлено сообщение
    private String topic;

    @Column(name = "type")
    private String type;

    // Сериализованный JSON-пейлоуд, который будет отправлен
    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status; // PENDING, SENT, FAILED

    private LocalDateTime createdAt;
}
