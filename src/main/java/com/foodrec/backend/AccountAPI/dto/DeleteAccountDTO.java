package com.foodrec.backend.AccountAPI.dto;

public class DeleteAccountDTO {

    private boolean descriptionDeletion;
    private boolean profileImageDeletion;
    private boolean backgroundImageDeletion;

    public DeleteAccountDTO() {
    }

    public DeleteAccountDTO(boolean descriptionDeletion, boolean profileImageDeletion, boolean backgroundImageDeletion) {
        this.descriptionDeletion = descriptionDeletion;
        this.profileImageDeletion = profileImageDeletion;
        this.backgroundImageDeletion = backgroundImageDeletion;
    }

    public boolean isDescriptionDeletion() {
        return descriptionDeletion;
    }

    public void setDescriptionDeletion(boolean descriptionDeletion) {
        this.descriptionDeletion = descriptionDeletion;
    }

    public boolean isProfileImageDeletion() {
        return profileImageDeletion;
    }

    public void setProfileImageDeletion(boolean profileImageDeletion) {
        this.profileImageDeletion = profileImageDeletion;
    }

    public boolean isBackgroundImageDeletion() {
        return backgroundImageDeletion;
    }

    public void setBackgroundImageDeletion(boolean backgroundImageDeletion) {
        this.backgroundImageDeletion = backgroundImageDeletion;
    }
}
