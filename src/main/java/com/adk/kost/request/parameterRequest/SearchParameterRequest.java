package com.adk.kost.request.parameterRequest;

import com.adk.kost.request.BaseSearchRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParameterRequest extends BaseSearchRequest {

    private String searchVal;

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }


}
