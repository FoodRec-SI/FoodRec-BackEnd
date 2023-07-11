package com.foodrec.backend.RatingAPI.entity;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "rating")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @EmbeddedId
    private RatingCompositeKey id;

    @Column(name = "score")
    private int score;

    @ManyToOne //Many Ratings can belong to 1 Post
    @MapsId("postId") /*name of the column of the Post table,
                        which is connected by the Rating table.*/
    @JoinColumn(name = "postid") /*name of the column of the Rating
                                    table, that matches the postid
                                    column of Post table*/
    private Post post;

    @ManyToOne //Many Ratings can belong to 1 Account
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private Account account;
}
