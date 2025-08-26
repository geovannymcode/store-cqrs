package com.geovannycode.store.common;

import java.util.List;

public record PagedResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {
    /**
     * Crear resultado paginado con cálculos automáticos.
     */
    public static <T> PagedResult<T> of(List<T> content, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        boolean hasNext = page < totalPages - 1;
        boolean hasPrevious = page > 0;

        return new PagedResult<>(content, page, size, totalElements, totalPages, hasNext, hasPrevious);
    }
}
