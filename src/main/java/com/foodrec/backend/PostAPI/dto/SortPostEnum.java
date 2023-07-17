package com.foodrec.backend.PostAPI.dto;

public enum SortPostEnum {
    CREATED_TIME("createdTime"),
    AVERAGE_SCORE("averageScore");
    private String sortField;

    SortPostEnum(String sortField) {
        this.sortField = sortField;
    }

    public String getSortField() {
        return sortField;
    }
}
