package com.geovannycode.store.products.command;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Product.ProductIdentifier> {
}
