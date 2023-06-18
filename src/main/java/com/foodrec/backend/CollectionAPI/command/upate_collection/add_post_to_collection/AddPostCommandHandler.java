package com.foodrec.backend.CollectionAPI.command.upate_collection.add_post_to_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.PostCollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
public class AddPostCommandHandler implements Command.Handler<AddPostCommand, HttpStatus> {
    private final CollectionRepository collectionRepository;
    private final PostRepository postRepository;

    public AddPostCommandHandler(CollectionRepository collectionRepository, PostRepository postRepository) {
        this.collectionRepository = collectionRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(AddPostCommand command) {
        PostCollectionDTO postCollectionDTO = command.getPostCollectionDTO();
        if (postCollectionDTO.getPostId() == null || postCollectionDTO.getPostId().isBlank() ||
                postCollectionDTO.getCollectionId() == null || postCollectionDTO.getCollectionId().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        if (postRepository.findById(postCollectionDTO.getPostId()).isEmpty() || collectionRepository.findById(postCollectionDTO.getCollectionId()).isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        if (!command.getUserId().equals(collectionRepository.findById(postCollectionDTO.getCollectionId()).get().getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to add post to the collection!");
        }
        Post post = postRepository.findById(postCollectionDTO.getPostId()).get();
        Collection collection = collectionRepository.findById(postCollectionDTO.getCollectionId()).get();
        Set<Collection> collectionSet = post.getCollections();
        collectionSet.add(collection);
        post.setCollections(collectionSet);
        postRepository.save(post);
        return HttpStatus.OK;
    }
}
