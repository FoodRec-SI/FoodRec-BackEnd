package com.foodrec.backend.CollectionAPI.command.delete_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.DeleteCollectionDTO;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import com.foodrec.backend.PostAPI.repository.PostCollectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCollectionCommandHandler implements Command.Handler<DeleteCollectionCommand, HttpStatus> {
    private final CollectionRepository collectionRepository;
    private final PostCollectionRepository postCollectionRepository;

    public DeleteCollectionCommandHandler(CollectionRepository collectionRepository, PostCollectionRepository postCollectionRepository) {
        this.collectionRepository = collectionRepository;
        this.postCollectionRepository = postCollectionRepository;
    }

    @Transactional
    @Override
    public HttpStatus handle(DeleteCollectionCommand command) {
        DeleteCollectionDTO deleteCollectionDTO = command.getCollectionDTO();
        if (deleteCollectionDTO.getCollectionId() == null || deleteCollectionDTO.getCollectionId().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        if (!command.getUserId().equals(collectionRepository.findById(deleteCollectionDTO.getCollectionId()).get().getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to delete this collection");
        }
        if (collectionRepository.findById(deleteCollectionDTO.getCollectionId()).isEmpty()) {
            throw new NotFoundExceptionHandler("Not found collection !");
        }
        String collectionId = deleteCollectionDTO.getCollectionId();
        postCollectionRepository.deleteAllByCollection_CollectionId(collectionId);
        collectionRepository.deleteById(collectionId);
        return HttpStatus.OK;
    }
}
