package com.foodrec.backend.PostAPI.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "post", createIndex = true)
public class PostELK implements Persistable<String> {
    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String postId;
    @Field(type = FieldType.Text)
    private String recipeId;
    @Field(type = FieldType.Text)
    private String recipeName;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Integer)
    private int duration;
    @Field(type = FieldType.Text)
    private String image;
    @Field(type = FieldType.Double)
    private double averageScore;

    @Override
    public boolean isNew() {
        return true;
    }
}
