package com.geovannycode.store.catalog.web;

import com.geovannycode.store.catalog.domain.Product;
import com.geovannycode.store.catalog.domain.ProductQueryService;
import com.geovannycode.store.catalog.domain.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService queryService;

    @GetMapping
    List<ProductView> getAllProducts() {
        return queryService.findAllProducts();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductView> getProductById(@PathVariable Product.ProductIdentifier id) {
        return ResponseEntity.of(queryService.findProductById(id));
    }

    @GetMapping("/by-category")
    List<ProductView> getProductsByCategory(@RequestParam String category) {
        return queryService.findProductsByCategory(category);
    }

    @GetMapping("/by-price")
    List<ProductView> getProductsByPriceRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return queryService.findProductsByPriceRange(min, max);
    }

    @GetMapping("/by-rating")
    List<ProductView> getProductsByRating() {
        return queryService.findProductsOrderedByRating();
    }
}
