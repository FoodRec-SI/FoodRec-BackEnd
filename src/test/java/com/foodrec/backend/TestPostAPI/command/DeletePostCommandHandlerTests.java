package com.foodrec.backend.TestPostAPI.command;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.PostAPI.command.delete_post.DeletePostCommand;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class DeletePostCommandHandlerTests {

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
    public void testDeletePostSuccessfully() {
        DeletePostDTO deletePostDTO = new DeletePostDTO();
        deletePostDTO.setPostId("POS000001");
        deletePostDTO.setUserName("namsieuquay");
        DeletePostCommand command = new DeletePostCommand(deletePostDTO);
        Boolean testPost = pipeline.send(command);
        assertTrue(testPost);
    }

    @Test
    public void testNullDataDeletePostByUser() {
        DeletePostDTO deletePostDTO = new DeletePostDTO();
        deletePostDTO.setPostId(null);
        deletePostDTO.setUserName(null);
        DeletePostCommand command = new DeletePostCommand(deletePostDTO);
        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "Invalid post!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testWrongUserNameDataDeletePostByUser() {
        DeletePostDTO deletePostDTO = new DeletePostDTO();
        deletePostDTO.setPostId("POS000004");
        deletePostDTO.setUserName("namsieuquay1");
        DeletePostCommand command = new DeletePostCommand(deletePostDTO);
        Exception exception = assertThrows(UnauthorizedExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "You are not authorized to delete this post!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testWrongPostDataDeletePostByUser() {
        DeletePostDTO deletePostDTO = new DeletePostDTO();
        deletePostDTO.setPostId("POS000009");
        deletePostDTO.setUserName("namsieuquay");
        DeletePostCommand command = new DeletePostCommand(deletePostDTO);
        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
            pipeline.send(command);
        });
        String expectedMessage = "Post cannot found!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
