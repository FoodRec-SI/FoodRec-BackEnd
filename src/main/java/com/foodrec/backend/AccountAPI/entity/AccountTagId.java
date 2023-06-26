package com.foodrec.backend.AccountAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTagId implements Serializable {

    @Column(name = "userid")
    private String userId;
    @Column(name = "tagid")
    private String tagId;

}
