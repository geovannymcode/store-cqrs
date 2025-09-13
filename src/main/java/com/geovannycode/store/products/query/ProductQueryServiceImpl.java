package com.geovannycode.store.products.query;

import com.geovannycode.store.products.command.ProductIdentifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductViewRepository repository;

    @Override
    public List<ProductView> findAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<ProductView> findProductById(ProductIdentifier id) {
        return repository.findById(id);
    }

    @Override
    public List<ProductView> findProductsByCategory(String category) {
        return repository.findByCategory(category);
    }

    @Override
    public List<ProductView> findProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return repository.findByPriceRange(min, max);
    }

    @Override
    public List<ProductView> findProductsOrderedByRating() {
        return repository.findAllOrderByRatingDesc();
    }
}
