package com.geovannycode.store.orders.domain;

import com.geovannycode.store.products.command.ProductIdentifier;
import com.geovannycode.store.products.query.ProductQueryService;
import com.geovannycode.store.products.query.ProductView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {

    private final ProductQueryService productQueryService;

    public ProductView getProductById(UUID productId) {
        ProductIdentifier identifier = new ProductIdentifier(productId);
        return productQueryService.findProductById(identifier)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
    }
}
