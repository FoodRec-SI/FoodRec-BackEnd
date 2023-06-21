package com.foodrec.backend.AccountAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountDTO {

    private boolean descriptionDeletion;
    private boolean profileImageDeletion;
    private boolean backgroundImageDeletion;

}
