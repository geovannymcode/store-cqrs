package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface InventoryRepository extends CrudRepository<InventoryEntity, ProductIdentifier> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<InventoryEntity> findWithLockingByProductId(ProductIdentifier productId);
    Iterable<InventoryEntity> findByAvailableStockLessThan(Integer threshold);
}
