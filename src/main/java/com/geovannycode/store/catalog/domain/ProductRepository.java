package com.geovannycode.store.catalog.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Product.ProductIdentifier> {
}
