package com.geovannycode.store.orders.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface OrderRepository extends CrudRepository<OrderEntity, OrderEntity.OrderIdentifier> {


    List<OrderEntity> findByCustomerEmail(String email);
    List<OrderEntity> findByStatus(String status);
}
