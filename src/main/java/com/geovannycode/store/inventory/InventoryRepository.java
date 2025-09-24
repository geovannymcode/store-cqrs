package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface InventoryRepository extends CrudRepository<InventoryEntity, ProductIdentifier> {

    Optional<InventoryEntity> findByProductId(ProductIdentifier productId);
    Iterable<InventoryEntity> findByAvailableStockLessThan(Integer threshold);
}
