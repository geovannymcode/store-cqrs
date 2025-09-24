package com.geovannycode.store.orders.domain.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.types.ValueObject;

@Getter
@NoArgsConstructor
public class Customer implements ValueObject {

    private String name;
    private String email;
    private String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
