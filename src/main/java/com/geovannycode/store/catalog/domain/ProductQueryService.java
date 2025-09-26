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

    /**
     * Guarda una vista de producto.
     * @param view La vista de producto a guardar
     * @return La vista guardada
     */
    @Transactional(readOnly = false)
    public ProductView saveProductView(ProductView view) {
        return repository.save(view);
    }
}
