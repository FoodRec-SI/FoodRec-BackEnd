package com.foodrec.backend.PostAPI.command.delete_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.dto.DeletePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostElasticsearchRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DeletePostCommandHandler implements Command.Handler<DeletePostCommand, HttpStatus> {

    private final PostRepository postRepository;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final RedisTemplate redisTemplate;

    public DeletePostCommandHandler(PostRepository postRepository, PostElasticsearchRepository postElasticsearchRepository, RedisTemplate redisTemplate) {
        this.postRepository = postRepository;
        this.postElasticsearchRepository = postElasticsearchRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    @Override
    public HttpStatus handle(DeletePostCommand command) {
        DeletePostDTO deletePostDTO = command.getDeletePostDTO();
        if (deletePostDTO.getPostId() == null || command.getUserId() == null) {
            throw new InvalidDataExceptionHandler("Invalid post!");
        }
        //Check if the postid is not exist
        Optional<Post> postOptional = postRepository.findById(deletePostDTO.getPostId());
        if (postOptional.isEmpty()) {
            throw new NotFoundExceptionHandler("Post cannot found!");
        }
        //Check if the user is authorized to delete this post
        if (!postOptional.get().getUserId().equals(command.getUserId())) {
            throw new UnauthorizedExceptionHandler("You are not authorized to delete this post!");
        }
        Post post = postOptional.get();
        post.setStatus(3);
        postRepository.save(post);
        postElasticsearchRepository.deletePostELKByPostId(post.getPostId());
        redisTemplate.delete("post::" + deletePostDTO.getPostId());
        return HttpStatus.OK;
    }
}
