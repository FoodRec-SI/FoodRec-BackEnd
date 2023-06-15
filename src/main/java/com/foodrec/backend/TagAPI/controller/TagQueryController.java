package com.foodrec.backend.TagAPI.controller;

import an.awesome.pipelinr.Pipeline;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import com.foodrec.backend.TagAPI.query.get_all_tag.GetAllTagQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/api/public/tag")
    public ResponseEntity getAllTag() {
        GetAllTagQuery query = new GetAllTagQuery();
        List<TagDTO> result = pipeline.send(query);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
