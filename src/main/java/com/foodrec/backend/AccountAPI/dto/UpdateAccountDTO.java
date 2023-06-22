package com.foodrec.backend.AccountAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO implements Serializable {

    private String description;
    private MultipartFile profileImage;
    private MultipartFile backgroundImage;
}
