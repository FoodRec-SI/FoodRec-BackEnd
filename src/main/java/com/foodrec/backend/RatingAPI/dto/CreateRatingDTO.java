package com.foodrec.backend.RatingAPI.dto;

public class CreateRatingDTO {

    private String postId;
    private int score;


    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
