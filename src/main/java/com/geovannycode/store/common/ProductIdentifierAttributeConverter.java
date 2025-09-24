package com.geovannycode.store.common;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = false)
public class ProductIdentifierAttributeConverter
        implements AttributeConverter<ProductIdentifier, UUID> {

    @Override public UUID convertToDatabaseColumn(ProductIdentifier attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override public ProductIdentifier convertToEntityAttribute(UUID dbData) {
        return dbData == null ? null : new ProductIdentifier(dbData);
    }
}
