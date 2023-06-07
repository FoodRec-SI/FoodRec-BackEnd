package com.foodrec.backend.PostAPI.entity;

public enum PostStatus {
    PENDING_APPROVAL(1),

    APPROVED(2),

    DELETED(3);

    private int value;

    PostStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
