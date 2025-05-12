package com.adk.kost.request;


import lombok.NonNull;


public class BaseSearchRequest {
    @NonNull
//    @Min(1)
    private Integer pageNo;

    @NonNull
//    @Min(1)
    private Integer pageSize;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
