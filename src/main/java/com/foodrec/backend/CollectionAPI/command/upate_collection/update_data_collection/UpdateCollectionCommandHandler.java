package com.foodrec.backend.CollectionAPI.command.upate_collection.update_data_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.UpdateCollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.Exception.UnauthorizedExceptionHandler;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class UpdateCollectionCommandHandler implements Command.Handler<UpdateCollectionCommand, HttpStatus> {
    private final CollectionRepository collectionRepository;
    private final ModelMapper modelMapper;

    public UpdateCollectionCommandHandler(CollectionRepository collectionRepository, ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public HttpStatus handle(UpdateCollectionCommand command) {
        UpdateCollectionDTO updateCollectionDTO = command.getCollectionDTO();
        if (updateCollectionDTO.getCollectionId() == null || updateCollectionDTO.getCollectionId().isBlank()) {
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        Optional<Collection> optionalCollection = collectionRepository.findById(updateCollectionDTO.getCollectionId());
        if (optionalCollection.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found collection!");
        }
        if (!optionalCollection.get().getUserId().equals(command.getUserId())) {
            throw new UnauthorizedExceptionHandler("You don't have permission to remove this post from the collection");
        }
        Collection collection = new Collection();
        String collectionName = updateCollectionDTO.getCollectionName() == null ? optionalCollection.get().getCollectionName() : updateCollectionDTO.getCollectionName();
        collection.setCollectionName(collectionName);
        String description = updateCollectionDTO.getDescription() == null ? optionalCollection.get().getDescription() : updateCollectionDTO.getDescription();
        collection.setDescription(description);
        collection.setCollectionId(updateCollectionDTO.getCollectionId());
        collection.setUserId(command.getUserId());
        collectionRepository.save(collection);
        return HttpStatus.OK;
    }
}
