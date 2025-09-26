package com.geovannycode.store.catalog.web.dto;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category
) {}
