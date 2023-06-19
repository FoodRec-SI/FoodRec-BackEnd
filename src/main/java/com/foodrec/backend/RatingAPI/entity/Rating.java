package com.foodrec.backend.RatingAPI.entity;

import jakarta.persistence.*;

@Table(name = "rating")
@Entity
@IdClass(RatingCompositeKey.class)
public class Rating {
    /*Reason for setting IDs for BOTH userId and postId:
    * If we only set @Id to userId, when we UPDATE the score of an existing Rating (postId,score),
    * JpaRepository will ONLY find the matching userId, and UPDATES ALL FIELDS (postId,score)
    * This means, the old postId and score will be OVERRIDDEN.

    * To fix this, we will ASSIGN BOTH userId and postId as COMPOSITE KEYS (using the @IdClass).
    * This helps the save() function understands that:
    *   - If BOTH userId and postId match, then UPDATE.
    *   - If ONLY userId, OR postId matches, OR NONE matches, then CREATE.
    * */
    @Id
    @Column(name = "userid")
    private String userId;
    @Id
    @Column(name = "postid")
    private String postId;
    @Column(name = "score")
    private int score;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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
