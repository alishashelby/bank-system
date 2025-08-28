package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
		scanBasePackages = {
				"com.dataaccess",
				"com.businesslogic",
				"com.presentation"
		}
)
@EnableJpaRepositories(
		basePackages = {
				"com.dataaccess.repositories"
})
@EntityScan(basePackages = {
		"com.dataaccess.entities"
})
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
