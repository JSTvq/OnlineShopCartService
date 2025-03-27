package com.kir138.model.entity;

import com.kir138.enumStatus.OutboxStatus;
import com.kir138.model.dto.ProductValidationResponse;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox_events")
@Builder
@Getter
@Setter
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
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private ProductValidationResponse payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status; // PENDING, SENT, FAILED

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
