package com.foodrec.backend.CollectionAPI.dto;

import com.foodrec.backend.PostAPI.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDetailsDTO {
    private String collectionName;
    private String description;
    private String image;
    Page<PostDTO> postDTOS;
}
