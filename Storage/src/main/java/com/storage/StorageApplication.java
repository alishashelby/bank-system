package com.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.dataaccess",
                "com.businesslogic",
        }
)
@EnableMongoRepositories(
        basePackages = {
                "com.dataaccess.repositories"
        }
)
@EntityScan(basePackages = {
        "com.dataaccess.entities"
})
public class StorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}