package com.geovannycode.store.orders.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID id,
        LocalDateTime createdAt,
        String status,
        CustomerDto customer,
        List<OrderItemDto> items,
        BigDecimal total
) {
    public record CustomerDto(
            String name,
            String email,
            String address
    ) {}

    public record OrderItemDto(
            UUID productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {}
}
