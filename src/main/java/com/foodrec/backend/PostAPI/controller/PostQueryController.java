package com.foodrec.backend.PostAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.InvalidPageInfoException;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.query.get_all_posts.GetAllPostsApprovedQuery;
import com.foodrec.backend.PostAPI.query.get_post_by_id.GetPostByIdQuery;
import com.foodrec.backend.PostAPI.query.get_post_by_id_by_moderator.GetPostByIdByModeratorQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_collection_id.GetPostByCollectionIdQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_recipe_name.GetPostsByRecipeNameQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_status_by_moderator.GetPostByStatusQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_tagId.GetPostsByTagIdQuery;
import com.foodrec.backend.PostAPI.query.get_posts_by_tagIds.GetPostsByTagIdsQuery;
import com.foodrec.backend.PostAPI.query.get_posts_liked_by_userid.GetPostsLikedByUserIdQuery;
import com.foodrec.backend.Utils.GetCurrentUserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@Tag(name = "PostAPI")
@RestController
public class PostQueryController {

    final Pipeline pipeline;

    public PostQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Get all posts from database which were approved.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/posts")
    public ResponseEntity getAllPostsApproved(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "6") int pageSize) {
        try {
            GetAllPostsApprovedQuery query = new GetAllPostsApprovedQuery(pageNumber, pageSize);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Operation(description = "Get all for moderator post by status: PENDING_APPROVAL OR APPROVED OR DELETED, you can choose 2 status.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/moderator/posts")
    public ResponseEntity<Page<PostDTO>> getAllPostsByStatus(@RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "6") int pageSize,
                                                             @RequestParam(required = true) List<PostStatus> postStatuses) {
        try {
            GetPostByStatusQuery query = new GetPostByStatusQuery(pageNumber, pageSize, postStatuses);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return new ResponseEntity<>(status);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(description = "Get posts by find recipename, but this function is not completed.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/posts/search")
    public ResponseEntity getPostsByRecipeName(@RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = "6") int pageSize,
                                               @RequestParam String recipeName) {
        try {
            GetPostsByRecipeNameQuery query = new GetPostsByRecipeNameQuery(pageNumber, pageSize, recipeName);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(description = "Get some posts which have tag ID.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/posts/{tagId}")
    public ResponseEntity getPostsByTagId(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "6") int pageSize,
                                          @RequestParam String tagId) {
        try {
            GetPostsByTagIdQuery query = new GetPostsByTagIdQuery(pageNumber, pageSize, tagId);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(description = "Get some posts which have some tags.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/posts/some/{tagIds}")
    public ResponseEntity getPostsByTagIds(@RequestParam(defaultValue = "0") int pageNumber,
                                           @RequestParam(defaultValue = "6") int pageSize,
                                           @RequestParam Set<String> tagIds) {
        try {
            GetPostsByTagIdsQuery query = new GetPostsByTagIdsQuery(pageNumber, pageSize, tagIds);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(description = "Get post by post ID.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/member/post/{postId}")
    public ResponseEntity getPostById(@PathVariable String postId) {
        try {
            GetPostByIdQuery query = new GetPostByIdQuery(postId);
            PostDTO result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(description = "Get post by post ID.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/moderator/post/{postId}")
    public ResponseEntity getPostByIdByModerator(@PathVariable String postId) {
        try {
            GetPostByIdByModeratorQuery query = new GetPostByIdByModeratorQuery(postId);
            PostDTO result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            description = "Gets the list of liked posts by a UserId.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @RequestMapping(value = "/api/member/like", method = RequestMethod.GET)
    public ResponseEntity getPostsLikedByUserId(@RequestParam(defaultValue = "0") String pageNumber,
                                                @RequestParam(defaultValue = "6") String pageSize) {
        ResponseEntity result = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            GetPostsLikedByUserIdQuery command =
                    new GetPostsLikedByUserIdQuery(userId, pageNumber, pageSize);
            Page<PostDTO> likedUserDTO = pipeline.send(command);
            if (likedUserDTO != null) {
                result = new ResponseEntity(likedUserDTO, HttpStatus.OK);
            }
        } catch (InvalidPageInfoException e) {
            result = new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }
        return result;
    }

    @Operation(description = "Get all posts in collection, use by collectionID",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/member/posts/{collectionId}")
    public ResponseEntity getPostsByCollectionId(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "6") int pageSize,
                                                 @PathVariable String collectionId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = GetCurrentUserData.getCurrentUserId(authentication);
            GetPostByCollectionIdQuery query = new GetPostByCollectionIdQuery(pageNumber, pageSize, userId, collectionId);
            Page<PostDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}