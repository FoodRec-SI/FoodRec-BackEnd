package com.foodrec.backend.TestPostAPI.command;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.command.create_post.CreatePostCommand;
import com.foodrec.backend.PostAPI.dto.CreatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class CreatePostCommandHandlerTests {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private Pipeline pipeline;
    @Container
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:11-alpine")
                    .withDatabaseName("foodrec-db")
                    .withUsername("${POSTGRESQL_DATABASE_NAME}")
                    .withPassword("${POSTGRESQL_DATABASE_PASSWORD}");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void testCreatePostSuccessfully() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setRecipeId("REC000025");
        String userId = "74007e14-840e-44f0-bc8c-99e3e9d1674c";
        CreatePostCommand command = new CreatePostCommand(createPostDTO, userId);
        String postDTOTest = pipeline.send(command);
        Optional<Post> post = postRepository.findById(postDTOTest);
        assertEquals(createPostDTO.getRecipeId(), post.get().getRecipeId());
    }

    @Test
    public void testNullDataCreatePost() {
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setRecipeId(null);
        String userId = "";
        CreatePostCommand command = new CreatePostCommand(createPostDTO, userId);
        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "Invalid post!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testDuplicateRecipeData(){
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setRecipeId("REC000016");
        String userId = "74007e14-840e-44f0-bc8c-99e3e9d1674c";
        CreatePostCommand command = new CreatePostCommand(createPostDTO, userId);
        Exception exception = assertThrows(DuplicateExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "Duplicate recipe!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testWrongRecipeData(){
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setRecipeId("REC00000");
        String userId = "74007e14-840e-44f0-bc8c-99e3e9d1674c";
        CreatePostCommand command = new CreatePostCommand(createPostDTO, userId);
        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "Recipe not found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testWrongUserIdData(){
        CreatePostDTO createPostDTO = new CreatePostDTO();
        createPostDTO.setRecipeId("REC000017");
        String userId = "59b8fdc0-42df-4a28-bcb1-e0651dbb08a1";
        CreatePostCommand command = new CreatePostCommand(createPostDTO, userId);
        Exception exception = assertThrows(UnauthorizedExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "You are not allowed to create post!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

