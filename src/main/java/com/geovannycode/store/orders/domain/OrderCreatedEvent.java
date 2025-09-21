package com.geovannycode.store.orders.domain;

import org.jmolecules.event.types.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record OrderCreatedEvent(
        UUID orderId,
        LocalDateTime createdAt,
        String customerName,
        String customerEmail,
        BigDecimal totalAmount,
        List<OrderItemDto> items
) implements DomainEvent {
    public record OrderItemDto(
            UUID productId,
            Integer quantity,
            BigDecimal unitPrice
    ) {}
}
