package com.foodrec.backend.LikeAPI.entity;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Likes { /*The temporary table, for
                        the M-M relationship between Post and Account.*/

    @EmbeddedId
    private LikesCompositeKey id;
    @ManyToOne /*Many Likes belong to 1 Post
                  (1 Post contains Many Likes) */
    @JoinColumn(name = "postid") /*name of the column of the Likes
                                    table, that matches the postid
                                    column of Post table*/
    @MapsId("postid") /*name of the column of the Post table,
                        which is connected by the Likes table.*/
    private Post post;


    @ManyToOne
    @JoinColumn(name = "userid")
    @MapsId("userid")
    private Account account;


    public LikesCompositeKey getId() {
        return id;
    }

    public void setId(LikesCompositeKey id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
