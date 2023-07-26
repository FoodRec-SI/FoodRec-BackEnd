package com.foodrec.backend.PostAPI.command.add_post_to_postELK;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.SearchPostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostELK;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostElasticsearchRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AddPostToPostELKCommandHandler implements Command.Handler<AddPostToPostELKCommand, Boolean> {
    private final PostRepository postRepository;
    private final PostElasticsearchRepository postElasticsearchRepository;
    private final ModelMapper modelMapper;

    public AddPostToPostELKCommandHandler(PostRepository postRepository, PostElasticsearchRepository postElasticsearchRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.postElasticsearchRepository = postElasticsearchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Boolean handle(AddPostToPostELKCommand command) {
        Iterable<PostELK> postELKS1 = postElasticsearchRepository.findAll();
        List<PostELK> postELKList = StreamSupport.stream(postELKS1.spliterator(), false).toList();
        List<Post> postList = postRepository.findAllByStatus(PostStatus.APPROVED.getValue());
        if (postList.size() == postELKList.size()) {
            return false;
        }
        postElasticsearchRepository.deleteAll();
        List<SearchPostDTO> searchPostDTOS = postList.stream().map(post -> modelMapper.map(post, SearchPostDTO.class)).toList();
        List<PostELK> postELKS = searchPostDTOS.stream().map(searchPostDTO -> {
            PostELK postELK = new PostELK();
            postELK.setPostId(searchPostDTO.getPostId());
            postELK.setRecipeId(searchPostDTO.getRecipeId());
            postELK.setRecipeName(searchPostDTO.getRecipeName());
            postELK.setDescription(searchPostDTO.getDescription());
            postELK.setDuration(searchPostDTO.getDuration());
            postELK.setImage(searchPostDTO.getImage());
            postELK.setAverageScore(searchPostDTO.getAverageScore());
            return postELK;
        }).collect(Collectors.toList());
        postElasticsearchRepository.saveAll(postELKS);
        return true;
    }
}
