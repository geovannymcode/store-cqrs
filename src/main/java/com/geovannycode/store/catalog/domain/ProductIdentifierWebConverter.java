package com.geovannycode.store.common;

import com.geovannycode.store.catalog.command.ProductIdentifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductIdentifierWebConverter implements Converter<String, ProductIdentifier> {
    @Override public ProductIdentifier convert(String source) {
        return ProductIdentifier.fromString(source);
    }
}
