package com.foodrec.backend.CollectionAPI.command.create_collection;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.CollectionAPI.dto.CreateCollectionDTO;
import com.foodrec.backend.CollectionAPI.entity.Collection;
import com.foodrec.backend.CollectionAPI.repository.CollectionRepository;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Utils.IdGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateCollectionCommandHandler implements Command.Handler<CreateCollectionCommand, HttpStatus> {
    private final CollectionRepository collectionRepository;
    private final ModelMapper modelMapper;


    public CreateCollectionCommandHandler(CollectionRepository collectionRepository, ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public HttpStatus handle(CreateCollectionCommand command) {
        CreateCollectionDTO createCollectionDTO = command.getCreateCollectionDTO();
        if(createCollectionDTO.getCollectionName() == null || createCollectionDTO.getCollectionName().isBlank() ||
                createCollectionDTO.getDescription() == null || createCollectionDTO.getDescription().isBlank()){
            throw new InvalidDataExceptionHandler("Invalid Data!");
        }
        String collectionId = IdGenerator.generateNextId(Collection.class, "collectionId");
        Collection collection = modelMapper.map(createCollectionDTO, Collection.class);
        collection.setCollectionId(collectionId);
        collection.setUserId(command.getUserId());
        collectionRepository.save(collection);
        return HttpStatus.OK;
    }
}
