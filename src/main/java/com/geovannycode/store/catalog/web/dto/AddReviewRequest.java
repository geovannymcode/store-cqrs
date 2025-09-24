package com.geovannycode.store.catalog.dto.command;

public record AddReviewRequest(
        Integer vote,
        String comment
) {}
