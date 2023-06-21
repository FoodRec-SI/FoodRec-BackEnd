package com.foodrec.backend.AccountAPI.dto;

import com.foodrec.backend.Utils.GetCurrentUserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private String userId = GetCurrentUserData.getCurrentUserId(authentication);
    private String userName = GetCurrentUserData.getCurrentUserName(authentication);
    private String profileImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/profile-default.jpg";
    private String backgroundImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/background-default.jpg";

}
