//package com.foodrec.backend.TestPostAPI.query;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
//import com.foodrec.backend.Exception.NotFoundExceptionHandler;
//import com.foodrec.backend.PostAPI.dto.PostDTO;
//import com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name.GetPostsByRecipeNameQuery;
//import com.foodrec.backend.PostAPI.repository.PostRepository;
//import org.junit.jupiter.api.Test;
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
//public class GetPostsByRecipeNameQueryHandlerTests {
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
//    static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }
//
//    @Test
//    public void testGetPostsByRecipeNameSuccessfully(){
//        GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery();
//        query.setRecipeName("B");
//        query.setPageNumber(0);
//        query.setPageSize(6);
//        Page<PostDTO> result = pipeline.send(query);
//        assertEquals(2, result.getContent().size());
//    }
//
//    @Test
//    public void testGetPostsByNullRecipeName(){
//        GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery();
//        query.setRecipeName("");
//        query.setPageNumber(0);
//        query.setPageSize(6);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testGetPostsByWrongPageNumber(){
//        GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery();
//        query.setRecipeName("B");
//        query.setPageNumber(-1);
//        query.setPageSize(6);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testGetPostsByWrongPageSize(){
//        GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery();
//        query.setRecipeName("B");
//        query.setPageNumber(0);
//        query.setPageSize(0);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testGetPostsButNotFound(){
//        GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery();
//        query.setRecipeName("e");
//        query.setPageNumber(0);
//        query.setPageSize(6);
//        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Not found!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
