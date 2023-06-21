package com.foodrec.backend.TagAPI.query.get_tags_by_recipeid;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class GetTagsByRecipeIdQueryHandler implements Command.Handler<GetTagsByRecipeIdQuery, List<TagDTO>> {
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    public GetTagsByRecipeIdQueryHandler(ModelMapper modelMapper, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Transactional
    @Override
    public List<TagDTO> handle(GetTagsByRecipeIdQuery query) {
        if (query.getRecipeId() == null || query.getRecipeId().equals("")) {
            throw new InvalidDataExceptionHandler("Invalid data!");
        }
        List<Tag> tagList = tagRepository.findTagsByRecipesRecipeId(query.getRecipeId());
        if (tagList.isEmpty()) {
            throw new NotFoundExceptionHandler("Not found tag!");
        }
        return tagList.stream().map(tag -> modelMapper.map(tag, TagDTO.class)).collect(Collectors.toList());
    }
}
