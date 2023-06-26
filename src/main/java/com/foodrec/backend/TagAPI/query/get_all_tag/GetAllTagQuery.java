package com.foodrec.backend.TagAPI.query.get_all_tag;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetAllTagQuery implements Command<List<TagDTO>> {
}

