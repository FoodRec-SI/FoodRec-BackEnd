package com.foodrec.backend.TagAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.Exception.InvalidDataExceptionHandler;
import com.foodrec.backend.Exception.NotFoundExceptionHandler;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.query.get_all_tag.GetAllTagQuery;
import com.foodrec.backend.TagAPI.query.get_tags_by_recipeid.GetTagsByRecipeIdQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.foodrec.backend.Config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "TagAPI")
public class TagQueryController {
    final Pipeline pipeline;

    public TagQueryController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(description = "Get all tags you have in database.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/tag")
    public ResponseEntity getAllTag() {
        GetAllTagQuery query = new GetAllTagQuery();
        List<TagDTO> result = pipeline.send(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(description = "Get all tags which recipe have.",
            security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/tag/{recipeId}")
    public ResponseEntity getTagByRecipeId(@PathVariable String recipeId) {
        try {
            GetTagsByRecipeIdQuery query = new GetTagsByRecipeIdQuery(recipeId);
            List<TagDTO> result = pipeline.send(query);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidDataExceptionHandler | NotFoundExceptionHandler e) {
            HttpStatus status = e.getClass().getAnnotation(ResponseStatus.class).value();
            return ResponseEntity.status(status).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
