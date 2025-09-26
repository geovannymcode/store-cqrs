package com.geovannycode.store.catalog.web.dto;

public record AddReviewRequest(
        Integer vote,
        String comment
) {}
