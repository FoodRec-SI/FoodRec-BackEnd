package com.foodrec.backend.CollectionAPI.query.get_collection_by_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDetailsDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostStatus;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GetCollectionByIdQueryHandler implements Command.Handler<GetCollectionByIdQuery, CollectionDetailsDTO> {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CollectionRepository collectionRepository;

    public GetCollectionByIdQueryHandler(PostRepository postRepository, ModelMapper modelMapper, CollectionRepository collectionRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.collectionRepository = collectionRepository;
    }


    @Override
    public CollectionDetailsDTO handle(GetCollectionByIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("recipeName").ascending());
        Optional<Collection> optionalCollection = collectionRepository.findById(query.getCollectionId());
        if (optionalCollection.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found collection");
        }
        if (!optionalCollection.get().getUserId().equals(query.getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to view collection");
        }
        Page<Post> postsPage = postRepository.getPostsByPostCollectionsCollectionAndStatus(optionalCollection.get(), 2, pageable);

        List<PostDTO> postDTOS = postsPage.getContent().stream().map(post -> {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTO.setPostStatus(PostStatus.convertStatusToEnum(post.getStatus()));
            return postDTO;
        }).toList();
        CollectionDetailsDTO collectionDetailsDTO = modelMapper.map(optionalCollection.get(), CollectionDetailsDTO.class);
        Page<PostDTO> postDTOPage = new PageImpl<>(postDTOS, pageable, postsPage.getTotalElements());
        Post post = postRepository.findFirstByPostCollections_CollectionAndStatusOrderByRecipeNameAsc(optionalCollection.get(), 2);
        if(post == null){
            collectionDetailsDTO.setPostDTOS(postDTOPage);
            return collectionDetailsDTO;
        }
        collectionDetailsDTO.setImage(post.getImage());
        collectionDetailsDTO.setPostDTOS(postDTOPage);
        return collectionDetailsDTO;
    }
}
