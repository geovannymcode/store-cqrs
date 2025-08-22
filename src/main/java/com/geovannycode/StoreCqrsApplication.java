package com.geovannycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@SpringBootApplication
@Modulithic
public class StoreCqrsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreCqrsApplication.class, args);
	}

}
