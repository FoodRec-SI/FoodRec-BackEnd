package com.foodrec.backend.PostAPI.entity;

import com.foodrec.backend.TagAPI.entity.Tag;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

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
    private String userName;
    @Field(type = FieldType.Text)
    private String moderatorName;
    @Field(type = FieldType.Text)
    private String recipeId;
    @Field(type = FieldType.Text, analyzer = "custom_analyzer.yml")
    private String recipeName;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Integer)
    private int calories;
    @Field(type = FieldType.Integer)
    private int duration;
    @Field(type = FieldType.Text)
    private String image;
    @CreatedDate
    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdTime;
    @CreatedDate
    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime verifiedTime;
    @Field(type = FieldType.Double)
    private double averageScore;
    @Field(type = FieldType.Text)
    private String ingredientList;
    @Field(type = FieldType.Text)
    private String instruction;

    @Override
    public boolean isNew() {
        return true;
    }
}
