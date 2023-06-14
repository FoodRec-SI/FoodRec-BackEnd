package com.foodrec.backend.TagAPI.query.get_all_tag;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import com.foodrec.backend.TagAPI.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class GetAllTagQueryHandler implements Command.Handler<GetAllTagQuery, List<TagDTO>>{
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public GetAllTagQueryHandler(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TagDTO> handle(GetAllTagQuery query) {
        List<Tag> tagList = tagRepository.findAll();
        List<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag: tagList){
            TagDTO tagDTO = modelMapper.map(tag, TagDTO.class);
            tagDTOs.add(tagDTO);
        }
        return tagDTOs;
    }
}
