package com.geovannycode.store.catalog.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductIdentifierWebConverter implements Converter<String, Product.ProductIdentifier> {
    @Override public Product.ProductIdentifier convert(String source) {
        return Product.ProductIdentifier.fromString(source);
    }
}
