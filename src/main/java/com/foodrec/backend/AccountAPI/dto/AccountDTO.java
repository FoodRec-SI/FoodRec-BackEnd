package com.foodrec.backend.AccountAPI.dto;

import com.foodrec.backend.TagAPI.dto.TagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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
