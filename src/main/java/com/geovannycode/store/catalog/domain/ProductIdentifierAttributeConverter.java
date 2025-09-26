package com.geovannycode.store.catalog.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = false)
public class ProductIdentifierAttributeConverter
        implements AttributeConverter<Product.ProductIdentifier, UUID> {

    @Override public UUID convertToDatabaseColumn(Product.ProductIdentifier attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override public Product.ProductIdentifier convertToEntityAttribute(UUID dbData) {
        return dbData == null ? null : new Product.ProductIdentifier(dbData);
    }
}
