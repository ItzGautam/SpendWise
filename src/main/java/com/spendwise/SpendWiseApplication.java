package com.spendwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpendWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpendWiseApplication.class, args);
	}

}
