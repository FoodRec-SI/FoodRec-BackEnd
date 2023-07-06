package com.foodrec.backend.LikeAPI.entity;

import com.foodrec.backend.AccountAPI.entity.Account;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "likes")
public class Likes { /*The temporary table, for
                        the M-M relationship between Post and Account.*/

    @EmbeddedId
    private LikesCompositeKey id;
    /*Quick explanation:
    * We have 3 tables: Post, Account, Likes.
     Because the Likes relationship between Post and Accounts
     *  is M-M, this is how it looks in the database
     *          Post (1) -------(M)-Likes-(M) ---------(1) Account
    *           (1 Post)         (can have Many Likes)      (from Many Accounts)
    *        or:(1 Account)      (can give Many Likes)      (to Many Posts)
    *
    * */
    @ManyToOne //Many Likes can belong to 1 Post
    @JoinColumn(name = "postid") /*name of the column of the Likes
                                    table, that matches the postid
                                    column of Post table*/
    @MapsId("postid") /*name of the column of the Post table,
                        which is connected by the Likes table.*/
    private Post post;


    @ManyToOne //Many Likes can belong to 1 Account
    @JoinColumn(name = "userid")
    @MapsId("userid")
    private Account account;


}
