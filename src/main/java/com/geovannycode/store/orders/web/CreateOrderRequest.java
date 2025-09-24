package com.geovannycode.store.orders.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        CustomerDto customer,
        List<OrderItemDto> items
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
            BigDecimal unitPrice
    ) {}
}
