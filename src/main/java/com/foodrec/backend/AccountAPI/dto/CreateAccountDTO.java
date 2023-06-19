package com.foodrec.backend.AccountAPI.dto;
import com.foodrec.backend.Utils.GetCurrentUserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class CreateAccountDTO {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    private String userId = GetCurrentUserData.getCurrentUserId(authentication);
    private String userName = GetCurrentUserData.getCurrentUserName(authentication);
    private String profileImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/profile-default.jpg";
    private String backgroundImage = "https://storage.googleapis.com/foodrec-389515.appspot.com/background-default.jpg";


    public CreateAccountDTO() {
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
