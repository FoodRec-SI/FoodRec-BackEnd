package com.foodrec.backend.AccountAPI.dto;

import org.springframework.web.multipart.MultipartFile;

public class UpdateAccountDTO {

    private String description;
    private MultipartFile profileImage;
    private MultipartFile backgroundImage;

    public UpdateAccountDTO() {
    }

    public UpdateAccountDTO(String description, MultipartFile profileImage, MultipartFile backgroundImage) {
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public MultipartFile getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(MultipartFile backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
