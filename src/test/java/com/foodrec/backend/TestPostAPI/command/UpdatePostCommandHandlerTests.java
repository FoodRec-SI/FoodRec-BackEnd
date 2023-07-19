//package com.foodrec.backend.TestPostAPI.command;
//
//import an.awesome.pipelinr.Pipeline;
//import com.foodrec.backend.PostAPI.command.update_post.UpdatePostCommand;
//import com.foodrec.backend.PostAPI.dto.PostDTO;
//import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
//import com.foodrec.backend.PostAPI.entity.Post;
//import com.foodrec.backend.PostAPI.entity.PostStatus;
//import com.foodrec.backend.PostAPI.repository.PostRepository;
//import com.foodrec.backend.Exception.DuplicateExceptionHandler;
//import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
//import com.foodrec.backend.Exception.NotFoundExceptionHandler;
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
//public class UpdatePostCommandHandlerTests {
//    @Autowired
//    private PostRepository postRepository;
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
//    public void testUpdatePostApprovedByModeratorSuccessfully() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId("POS000002");
//        updatePostDTO.setStatus(PostStatus.APPROVED);
//        String userid = "1f13ef69-46ad-42c7-aa00-04cd546c2164";
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, userid);
//        PostDTO postDTOTest = pipeline.send(command);
//        Optional<Post> post = postRepository.findById(postDTOTest.getPostId());
//        assertEquals(updatePostDTO.getPostId(), post.get().getPostId());
//        assertEquals(updatePostDTO.getStatus().getValue(), post.get().getStatus());
//        assertEquals(command.getUserId(), post.get().getModeratorId());
//    }
//
//    @Test
//    public void testUpdatePostDeletedByModeratorSuccessfully() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId("POS000002");
//        updatePostDTO.setStatus(PostStatus.DELETED);
//        String moderatorId = "1f13ef69-46ad-42c7-aa00-04cd546c2164";
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, moderatorId);
//        PostDTO postDTOTest = pipeline.send(command);
//        Optional<Post> post = postRepository.findById(postDTOTest.getPostId());
//        assertEquals(updatePostDTO.getPostId(), post.get().getPostId());
//        assertEquals(updatePostDTO.getStatus().getValue(), post.get().getStatus());
//        assertEquals(command.getUserId(), post.get().getModeratorId());
//    }
//
//    @Test
//    public void testNullDataUpdatePostByModerator() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId(null);
//        updatePostDTO.setStatus(null);
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, null);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(command);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testWrongPostData() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId("POS000005");
//        updatePostDTO.setStatus(PostStatus.APPROVED);
//        String moderatorId = "1f13ef69-46ad-42c7-aa00-04cd546c2164";
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, moderatorId);
//        Exception exception = assertThrows(NotFoundExceptionHandler.class, () -> {
//            pipeline.send(command);
//        });
//        String expectedMessage = "Post not found!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testReturnPendingPostData() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId("POS000001");
//        updatePostDTO.setStatus(PostStatus.PENDING_APPROVAL);
//        String moderatorId = "1f13ef69-46ad-42c7-aa00-04cd546c2164";
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, moderatorId);
//        Exception exception = assertThrows(InvalidDataExceptionHandler.class, () -> {
//            pipeline.send(command);
//        });
//        String expectedMessage = "Invalid data!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//
//    @Test
//    public void testDuplicatePostStatusData() {
//        UpdatePostDTO updatePostDTO = new UpdatePostDTO();
//        updatePostDTO.setPostId("POS000001");
//        updatePostDTO.setStatus(PostStatus.APPROVED);
//        String moderatorId = "1f13ef69-46ad-42c7-aa00-04cd546c2164";
//        UpdatePostCommand command = new UpdatePostCommand(updatePostDTO, moderatorId);
//        Exception exception = assertThrows(DuplicateExceptionHandler.class, () -> {
//            pipeline.send(command);
//        });
//        String expectedMessage = "Duplicate post status!";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//    }
//}