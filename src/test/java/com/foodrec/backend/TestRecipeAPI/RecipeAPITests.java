//package com.foodrec.backend.TestRecipeAPI;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.RecipeAPI.command.create_recipe.CreateRecipeCommand;
//import com.foodrec.backend.RecipeAPI.command.delete_recipe.DeleteRecipeCommand;
//import com.foodrec.backend.RecipeAPI.command.update_recipe.UpdateRecipeCommand;
//import com.foodrec.backend.RecipeAPI.dto.CreateRecipeDTO;
//import com.foodrec.backend.RecipeAPI.dto.RecipeDTO;
//import com.foodrec.backend.RecipeAPI.dto.UpdateRecipeDTO;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class RecipeAPITests {
//
//    @Autowired
//    private Pipeline pipeline;
//    @Container
//    public static PostgreSQLContainer postgreSQLContainer =
//            new PostgreSQLContainer("postgres:11-alpine")
//                    .withDatabaseName("foodrec-db")
//                    .withUsername("${POSTGRESQL_DATABASE_NAME}")
//                    .withPassword("${POSTGRESQL_DATABASE_PASSWORD}");
//
//    @DynamicPropertySource
//    static void properties(DynamicPropertyRegistry registry){
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }
//
//
//
//}