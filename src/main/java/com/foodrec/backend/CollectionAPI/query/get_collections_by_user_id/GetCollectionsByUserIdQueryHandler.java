package com.foodrec.backend.CollectionAPI.query.get_collections_by_user_id;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostCollectionRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetCollectionsByUserIdQueryHandler implements Command.Handler<GetCollectionsByUserIdQuery, Page<CollectionDTO>> {
    private final CollectionRepository collectionRepository;
    private final PostCollectionRepository postCollectionRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    public GetCollectionsByUserIdQueryHandler(CollectionRepository collectionRepository, PostCollectionRepository postCollectionRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.collectionRepository = collectionRepository;
        this.postCollectionRepository = postCollectionRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public Page<CollectionDTO> handle(GetCollectionsByUserIdQuery query) {
        if (query.getPageNumber() < 0 || query.getPageSize() < 1) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize(), Sort.by("collectionName").ascending());
        Page<Collection> collectionsPage = collectionRepository.findCollectionsByUserId(query.getUserId(), pageable);
        if (collectionsPage.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        List<CollectionDTO> collectionDTOs = new ArrayList<>();
        for (Collection collection : collectionsPage) {
            CollectionDTO collectionDTO = modelMapper.map(collection, CollectionDTO.class);
            Post post = postRepository.findFirstByPostCollections_CollectionAndStatusOrderByRecipeNameAsc(collection, 2);
            int countPost = postCollectionRepository.countByCollection_CollectionId(collection.getCollectionId());
            if (post != null) {
                String image = post.getImage();
                collectionDTO.setImage(image);
                collectionDTO.setCountPost(countPost);
            }
            collectionDTOs.add(collectionDTO);
        }
        return new PageImpl<>(collectionDTOs, pageable, collectionsPage.getTotalElements());
    }
}
