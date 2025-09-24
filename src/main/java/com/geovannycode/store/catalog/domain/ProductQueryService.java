package com.geovannycode.store.catalog.query;

import com.geovannycode.store.catalog.command.ProductIdentifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    List<ProductView> findAllProducts();
    Optional<ProductView> findProductById(ProductIdentifier id);
    List<ProductView> findProductsByCategory(String category);
    List<ProductView> findProductsByPriceRange(BigDecimal min, BigDecimal max);
    List<ProductView> findProductsOrderedByRating();
}
