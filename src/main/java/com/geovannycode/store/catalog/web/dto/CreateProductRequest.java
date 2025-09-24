package com.geovannycode.store.catalog.dto.command;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {}
