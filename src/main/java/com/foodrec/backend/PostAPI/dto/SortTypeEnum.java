package com.foodrec.backend.PostAPI.dto;

public enum SortTypeEnum {
    ACCENDING("ASC"),
    DESENDING("DESC");
    private String sortType;

    SortTypeEnum(String sortType) {
        this.sortType = sortType;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
