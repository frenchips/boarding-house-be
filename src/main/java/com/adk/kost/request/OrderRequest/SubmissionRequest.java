package com.adk.kost.request.OrderRequest;


import com.adk.kost.request.BaseSearchRequest;

public class SubmissionRequest extends BaseSearchRequest {
    private String searchValue;

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
}
