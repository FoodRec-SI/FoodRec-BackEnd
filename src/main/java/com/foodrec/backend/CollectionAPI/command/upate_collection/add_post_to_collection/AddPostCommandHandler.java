package com.foodrec.backend.CollectionAPI.command.upate_collection.add_post_to_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.PostCollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.entity.PostCollection;
import com.foodrec.backend.PostAPI.repository.PostCollectionRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AddPostCommandHandler implements Command.Handler<AddPostCommand, HttpStatus> {
    private final PostRepository postRepository;
    private final CollectionRepository collectionRepository;
    private final PostCollectionRepository postCollectionRepository;

    public AddPostCommandHandler(PostRepository postRepository,
                                 CollectionRepository collectionRepository,
                                 PostCollectionRepository postCollectionRepository) {
        this.postRepository = postRepository;
        this.collectionRepository = collectionRepository;
        this.postCollectionRepository = postCollectionRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(AddPostCommand command) throws InvalidDataExceptionHandler {
        PostCollectionDTO postCollectionDTO = command.getPostCollectionDTO();
        if (postCollectionDTO.getPostId() == null || postCollectionDTO.getPostId().isBlank() ||
                postCollectionDTO.getCollectionId() == null || postCollectionDTO.getCollectionId().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        Optional<Post> postOptional = postRepository.findById(postCollectionDTO.getPostId());
        Optional<Collection> collectionOptional = collectionRepository.findById(postCollectionDTO.getCollectionId());
        if (postOptional.isEmpty() || collectionOptional.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found!");
        }
        Collection collection = collectionOptional.get();
        if (!command.getUserId().equals(collection.getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to add post to the collection!");
        }
        Post post = postOptional.get();
        PostCollection postCollection = new PostCollection();
        postCollection.setCollection(collection);
        postCollection.setPost(post);
        postCollectionRepository.save(postCollection);
        return HttpStatus.OK;
    }
}
