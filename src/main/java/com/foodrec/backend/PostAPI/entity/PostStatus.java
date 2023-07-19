package com.foodrec.backend.PostAPI.entity;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Integer> convertPostStatusesToIntArray(List<PostStatus> postStatuses) {
        List<Integer> statusNum = new ArrayList<>();
        for (PostStatus postStatus : postStatuses) {
            statusNum.add(postStatus.getValue());
        }
        return statusNum;
    }

    public static PostStatus convertStatusToEnum(int status) {
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.getValue() == status) {
                return postStatus;
            }
        }
        return null;
    }
}