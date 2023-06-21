package com.foodrec.backend.AccountAPI.dto;

import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String name;
    private String description;
    private String profileImage;
    private String backgroundImage;
    private Collection<TagDTO> tagsCollection;

}
