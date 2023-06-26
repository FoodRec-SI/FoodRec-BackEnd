package com.foodrec.backend.AccountAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodrec.backend.PostAPI.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "userid")
    private String userId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "profile-image")
    private String profileImageName;
    @Column(name = "background-image")
    private String backgroundImageName;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AccountTag> accountTags;

    @ManyToMany(mappedBy = "accounts")
    private Set<Post> posts;
}
