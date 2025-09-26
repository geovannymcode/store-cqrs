package com.geovannycode.store.orders.domain.models;

import org.springframework.modulith.events.Externalized;

@Externalized("StoreExchange::orders.new")
public record OrderCreatedEvent(String orderNumber, String productCode, int quantity, Customer customer) {}
