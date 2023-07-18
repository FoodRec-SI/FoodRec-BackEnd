package com.foodrec.backend.PostAPI.dto;

import com.foodrec.backend.PostAPI.entity.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorPostDTO {
    private String postId;
    private String recipeName;
    private String userName;
    private LocalDateTime verifiedTime;
    private PostStatus postStatus;
}
