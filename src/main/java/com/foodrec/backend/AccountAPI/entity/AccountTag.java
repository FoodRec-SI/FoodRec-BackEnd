package com.foodrec.backend.AccountAPI.entity;

import com.foodrec.backend.RecipeAPI.entity.Recipe;
import com.foodrec.backend.RecipeAPI.entity.RecipeTagId;
import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_tag")
public class AccountTag {

    @EmbeddedId
    private AccountTagId accountTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userid", insertable=false, updatable=false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tagid", insertable=false, updatable=false )
    private Tag tag;

}
