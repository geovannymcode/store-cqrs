package com.geovannycode.store.inventory.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    Optional<InventoryEntity> findByProductCode(String productCode);
}
