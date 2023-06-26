package com.foodrec.backend.CollectionAPI.command.upate_collection.remove_post_from_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.RemovePostCollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.entity.Post;
import com.foodrec.backend.PostAPI.repository.PostCollectionRepository;
import com.foodrec.backend.PostAPI.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RemovePostCommandHandler implements Command.Handler<RemovePostCommand, HttpStatus> {
    private final CollectionRepository collectionRepository;
    private final PostRepository postRepository;
    private final PostCollectionRepository postCollectionRepository;

    public RemovePostCommandHandler(CollectionRepository collectionRepository, PostRepository postRepository, PostCollectionRepository postCollectionRepository) {
        this.collectionRepository = collectionRepository;
        this.postRepository = postRepository;
        this.postCollectionRepository = postCollectionRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(RemovePostCommand command) {
        RemovePostCollectionDTO removePostCollectionDTO = command.getRemovePostCollectionDTO();
        if (removePostCollectionDTO.getPostId() == null || removePostCollectionDTO.getPostId().isBlank() ||
                removePostCollectionDTO.getCollectionId() == null || removePostCollectionDTO.getCollectionId().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        Optional<Collection> optionalCollection = collectionRepository.findById(removePostCollectionDTO.getCollectionId());
        if (optionalCollection.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found collection!");
        }
        if (!optionalCollection.get().getUserId().equals(command.getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to remove this post from the collection");
        }
        Collection collection = optionalCollection.get();
        Optional<Post> optionalPost = postRepository.findById(removePostCollectionDTO.getPostId());
        if (optionalPost.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found post!");
        }
        Post post = optionalPost.get();
        postCollectionRepository.deleteByPostAndCollection(post, collection);
        return HttpStatus.OK;
    }
}
