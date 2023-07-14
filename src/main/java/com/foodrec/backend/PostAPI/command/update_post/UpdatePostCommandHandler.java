package com.foodrec.backend.PostAPI.command.update_post;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.AccountAPI.repository.AccountRepository;
import com.foodrec.backend.Exception.DuplicateExceptionHandler;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.dto.UpdatePostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostELK;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostElasticsearchRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UpdatePostCommandHandler implements Command.Handler<UpdatePostCommand, PostDTO> {
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final ModelMapper modelMapper;

    public UpdatePostCommandHandler(PostRepository postRepository, AccountRepository accountRepository, PostElasticsearchRepository postElasticsearchRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
        this.postElasticsearchRepository = postElasticsearchRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public PostDTO handle(UpdatePostCommand command) {
        UpdatePostDTO updatePostDTO = command.getUpdatePostDTO();
        LocalDateTime localDateTime = LocalDateTime.now();
        if (updatePostDTO.getStatus() == null || updatePostDTO.getPostId() == null ||
                updatePostDTO.getStatus().equals(PostStatus.PENDING_APPROVAL)) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Optional<Post> optionalPost = postRepository.findById(updatePostDTO.getPostId());
        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Post not found!");
        }
        if (optionalPost.get().getStatus() == updatePostDTO.getStatus().getValue()) {
            throw new DuplicateExceptionHandler("Duplicate post status!");
        }
        Post post = optionalPost.get();
        post.setStatus(updatePostDTO.getStatus().getValue());
        post.setModeratorId(command.getUserId());
        post.setVerifiedTime(localDateTime);
        postRepository.save(post);
        if (post.getStatus() == 3) {
            return modelMapper.map(post, PostDTO.class);
        }
        PostELK postELK = new PostELK();
        postELK.setPostId(post.getPostId());
        postELK.setUserName(accountRepository.findById(post.getUserId()).get().getName());
        postELK.setModeratorName(accountRepository.findById(command.getUserId()).get().getName());
        postELK.setRecipeId(post.getRecipeId());
        postELK.setRecipeName(post.getRecipeName());
        postELK.setDescription(post.getDescription());
        postELK.setCalories(post.getCalories());
        postELK.setDuration(post.getDuration());
        postELK.setImage(post.getImage());
        postELK.setCreatedTime(post.getCreatedTime());
        postELK.setVerifiedTime(post.getVerifiedTime());
        postELK.setAverageScore(post.getAverageScore());
        postELK.setIngredientList(post.getIngredientList());
        postELK.setInstruction(post.getInstruction());
        postElasticsearchRepository.save(postELK);
        return modelMapper.map(post, PostDTO.class);
    }
}
