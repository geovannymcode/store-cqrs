package com.geovannycode.store.orders.web;

import java.util.UUID;

public record CreateOrderResponse(
        UUID orderId,
        String status,
        String message
) {}
