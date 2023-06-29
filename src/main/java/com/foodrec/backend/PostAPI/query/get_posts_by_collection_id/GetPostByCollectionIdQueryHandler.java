package com.foodrec.backend.PostAPI.query.get_posts_by_collection_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GetPostByCollectionIdQueryHandler implements Command.Handler<GetPostByCollectionIdQuery, Page<PostDTO>> {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final CollectionRepository collectionRepository;

    public GetPostByCollectionIdQueryHandler(PostRepository postRepository, TagRepository tagRepository, ModelMapper modelMapper, CollectionRepository collectionRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
        this.collectionRepository = collectionRepository;
    }

    @Transactional
    @Override
    public Page<PostDTO> handle(GetPostByCollectionIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("recipeName").ascending());
        Page<Post> postsPage = postRepository.getPostsByPostCollectionsCollectionAndStatus(collectionRepository.findById(query.getCollectionId()).get(), 2, pageable);
        if (postsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            List<Tag> tagList = tagRepository.findTagsByRecipeTags_Recipe_RecipeId(postDTO.getRecipeId());
            List<TagDTO> tagDTOList = tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).toList();
            postDTO.setTagDTOList(tagDTOList);
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        return new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
    }
}
