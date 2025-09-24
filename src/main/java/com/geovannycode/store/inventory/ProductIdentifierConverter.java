package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter(autoApply = false)
public class ProductIdentifierConverter implements AttributeConverter<ProductIdentifier, UUID> {
    @Override
    public UUID convertToDatabaseColumn(ProductIdentifier attribute) {
        if (attribute == null) return null;
        return attribute.getId();
    }

    @Override
    public ProductIdentifier convertToEntityAttribute(UUID dbData) {
        if (dbData == null) return null;
        return new ProductIdentifier(dbData);
    }
}
