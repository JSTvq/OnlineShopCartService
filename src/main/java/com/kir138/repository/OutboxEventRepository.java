package com.kir138.repository;

import com.kir138.model.entity.OutboxEvent;
import com.kir138.enumStatus.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findAllByStatus(OutboxStatus status);
}
