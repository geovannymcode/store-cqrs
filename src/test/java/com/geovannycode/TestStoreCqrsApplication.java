package com.geovannycode;

import org.springframework.boot.SpringApplication;

public class TestStoreCqrsApplication {

	public static void main(String[] args) {
		SpringApplication.from(StoreCqrsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
