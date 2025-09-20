package com.geovannycode.store.orders.domain;

public enum OrderStatus {
    CREATED,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean isActive() {
        return this != DELIVERED && this != CANCELLED;
    }

    public boolean canBeCancelled() {
        return this == CREATED || this == PAID || this == PROCESSING;
    }
}
