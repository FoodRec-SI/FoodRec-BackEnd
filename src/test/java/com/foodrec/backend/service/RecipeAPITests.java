package com.foodrec.backend;

import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

public class RecipeAPITests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgresql:alpine")
            .withDatabaseName("foodrec-db")
            .withUsername("postgres")
            .withPassword("12345");
    @BeforeAll
    public static void setUp(){
        container.withReuse(true);
        container.withInitScript("src/main/resources/db.sql");
        container.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }
    @AfterAll
    public static void tearDown(){
        container.stop();
    }
}
