package com.foodrec.backend.AccountAPI.dto;
import com.foodrec.backend.Utils.GetCurrentUserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class CreateAccountDTO {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private String userId = GetCurrentUserData.getCurrentUserId(authentication);
    private String userName = GetCurrentUserData.getCurrentUserName(authentication);

    public CreateAccountDTO() {
    }

    public CreateAccountDTO(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
