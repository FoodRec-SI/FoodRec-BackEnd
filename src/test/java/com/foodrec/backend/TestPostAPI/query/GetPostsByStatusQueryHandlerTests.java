package com.foodrec.backend.TestPostAPI.query;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.query.get_posts_by_status_by_moderator.GetPostByStatusQuery;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class GetPostsByStatusQueryHandlerTests {
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
    public void testGetPostPending_ApprovalSuccessfully(){
        GetPostByStatusQuery query = new GetPostByStatusQuery();
        query.setPageSize(6);
        query.setPageNumber(0);
        query.setPostStatuses(Collections.singletonList(PostStatus.PENDING_APPROVAL));
        Page<PostDTO> result = pipeline.send(query);
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testGetPostApprovedAndDeletedSuccessfully(){
        GetPostByStatusQuery query = new GetPostByStatusQuery();
        query.setPageSize(6);
        query.setPageNumber(0);
        List<PostStatus> postStatuses = Arrays.asList(PostStatus.APPROVED, PostStatus.DELETED);
        query.setPostStatuses(postStatuses);
        Page<PostDTO> result = pipeline.send(query);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetPostsByWrongPageNumber(){
        GetPostByStatusQuery query = new GetPostByStatusQuery();
        query.setPageNumber(-1);
        query.setPageSize(6);
        List<PostStatus> postStatuses = Arrays.asList(PostStatus.APPROVED, PostStatus.DELETED);
        query.setPostStatuses(postStatuses);
        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
            pipeline.send(query);
        });
        String expectedMessage = "Invalid data!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPostsByWrongPageSize(){
        GetPostByStatusQuery query = new GetPostByStatusQuery();
        query.setPageNumber(0);
        query.setPageSize(0);
        List<PostStatus> postStatuses = Arrays.asList(PostStatus.APPROVED, PostStatus.DELETED);
        query.setPostStatuses(postStatuses);
        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
            pipeline.send(query);
        });
        String expectedMessage = "Invalid data!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
