package com.foodrec.backend.TagAPI.query.get_all_tag;

import an.awesome.pipelinr.Command;
import com.foodrec.backend.PostAPI.dto.PostDTO;
import com.foodrec.backend.TagAPI.dto.TagDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public class GetAllTagQuery implements Command<List<TagDTO>> {
    private String tagId;
    private String tagName;

    public GetAllTagQuery() {
    }

    public GetAllTagQuery(String tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

