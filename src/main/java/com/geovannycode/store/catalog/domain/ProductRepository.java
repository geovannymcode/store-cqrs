package com.geovannycode.store.catalog.command;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, ProductIdentifier> {
}
