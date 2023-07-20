//package com.foodrec.backend.TestPostAPI.query;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
//import com.foodrec.backend.Exception.NotFoundExceptionHandler;
//import com.foodrec.backend.PostAPI.dto.PostDTO;
//import com.foodrec.backend.PostAPI.entity.Post;
//import com.foodrec.backend.PostAPI.query.get_post_by_id.GetPostByIdQuery;
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
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Testcontainers
//@SpringBootTest
//public class GetPostByIdByModeratorQueryHandlerTests {
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
//    public void testGetPostByIdSuccessfully() {
//        String postId = "POS000001";
//        GetPostByIdQuery query = new GetPostByIdQuery(postId, "74007e14-840e-44f0-bc8c-99e3e9d1674c");
//        PostDTO postDTO = pipeline.send(query);
//        Optional<Post> optionalPost = postRepository.findPostByPostIdAndStatus(query.getPostId(), 2);
//        assertEquals(postDTO.getPostId(), optionalPost.get().getPostId());
//        assertEquals(postDTO.getRecipeName(), optionalPost.get().getRecipeName());
//        assertEquals(postDTO.getRecipeId(), optionalPost.get().getRecipeId());
//        assertEquals(postDTO.getUserId(), optionalPost.get().getUserId());
//    }
//
//    @Test
//    public void testNotFoundPost() {
//        String postId = "POS000009";
//        GetPostByIdQuery query = new GetPostByIdQuery(postId, "74007e14-840e-44f0-bc8c-99e3e9d1674c");
//        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Post not found!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testNullPostId() {
//        String postId = "";
//        GetPostByIdQuery query = new GetPostByIdQuery(postId, "74007e14-840e-44f0-bc8c-99e3e9d1674c");
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(query);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}
