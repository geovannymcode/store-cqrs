package com.geovannycode.store.products.dto.command;

public record AddReviewRequest(
        Integer vote,
        String comment
) {}
