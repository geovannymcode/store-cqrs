package com.geovannycode.store.products.command;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductIdentifierConverter implements Converter<String, ProductIdentifier> {
    @Override public ProductIdentifier convert(String source) {
        return ProductIdentifier.fromString(source);
    }
}

