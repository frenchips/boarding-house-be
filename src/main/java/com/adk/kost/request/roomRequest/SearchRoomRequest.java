package com.adk.kost.request.roomRequest;


import com.adk.kost.request.BaseSearchRequest;

public class SearchRoomRequest extends BaseSearchRequest {
    private String searchVal;

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }
}
