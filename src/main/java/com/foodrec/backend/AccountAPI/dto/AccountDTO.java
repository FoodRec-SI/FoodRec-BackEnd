package com.foodrec.backend.AccountAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private String name;
    private String description;
    private String profileImage;
    private String backgroundImage;

}
