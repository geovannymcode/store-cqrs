package com.geovannycode.store.products.dto.command;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {}
