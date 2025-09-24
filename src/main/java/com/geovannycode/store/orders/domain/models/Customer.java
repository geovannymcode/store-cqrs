package com.geovannycode.store.orders.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.types.ValueObject;

@Getter
@NoArgsConstructor
@Embeddable
public class Customer implements ValueObject {

    @Column(name = "customer_name", nullable = false, length = 255)
    private String name;

    @Column(name = "customer_email", nullable = false, length = 255)
    private String email;

    @Column(name = "customer_address", nullable = false, columnDefinition = "text")
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
