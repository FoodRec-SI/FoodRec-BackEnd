package com.foodrec.backend.AccountAPI.dto;

public class AccountDTO {

    private String name;
    private String description;
    private byte[] profileImage;
    private byte[] backgroundImage;

    public AccountDTO() {
    }

    public AccountDTO(String name, String description, byte[] profileImage, byte[] backgroundImage) {
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public byte[] getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(byte[] backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
