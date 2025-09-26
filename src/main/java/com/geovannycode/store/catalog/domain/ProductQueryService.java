package com.geovannycode.store.catalog.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductViewRepository repository;

    public List<ProductView> findAllProducts() {
        return repository.findAll();
    }

    public Optional<ProductView> findProductById(Product.ProductIdentifier id) {
        return repository.findById(id);
    }

    public List<ProductView> findProductsByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<ProductView> findProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    public List<ProductView> findProductsOrderedByRating() {
        return repository.findAllOrderByRatingDesc();
    }
}
