package com.geovannycode.store.catalog.dto.command;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {}
