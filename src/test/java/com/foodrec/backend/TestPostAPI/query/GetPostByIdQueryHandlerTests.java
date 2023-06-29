//package com.foodrec.backend.TestPostAPI.query;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
//import com.foodrec.backend.Exception.NotFoundExceptionHandler;
//import com.foodrec.backend.PostAPI.dto.PostDTO;
//import com.foodrec.backend.PostAPI.entity.Post;
//import com.foodrec.backend.PostAPI.repository.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//@SpringBootTest
//public class GetPostByIdQueryHandlerTests {
//    @Autowired
//    private PostRepository postRepository;
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
//    public void testGetPostByIdSuccessfully(){
//        String postId = "POS000001";
//        GetPostById query = new GetPostById(postId);
//        PostDTO postDTO = pipeline.send(query);
//        Optional<Post> optionalPost = postRepository.findPostByPostIdAndStatus(query.getPostId(), 2);
//        assertEquals(postDTO.getPostId(), optionalPost.get().getPostId());
//        assertEquals(postDTO.getRecipeName(), optionalPost.get().getRecipeName());
//        assertEquals(postDTO.getRecipeId(), optionalPost.get().getRecipeId());
//        assertEquals(postDTO.getUserId(), optionalPost.get().getUserId());
//    }
//
//    @Test
//    public void testNotFoundPost(){
//        String postId = "POS000009";
//        GetPostById query = new GetPostById(postId);
//        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Post not found!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testNullPostId(){
//        String postId = "";
//        GetPostById query = new GetPostById(postId);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
