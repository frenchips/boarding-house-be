package com.adk.kost.request.OrderRequest;


import com.adk.kost.request.BaseSearchRequest;

public class HomeOrderRequest extends BaseSearchRequest {

    private String searchVal;

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }
}
