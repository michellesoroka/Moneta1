package com.example.Moneta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableMongoRepositories
public class MonetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonetaApplication.class, args);
	}

}
