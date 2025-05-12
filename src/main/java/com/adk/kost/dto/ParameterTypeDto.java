package com.adk.kost.dto;

import java.util.List;

public class ParameterTypeDto  {

    private String paramTypeCode;
    private String paramTypeName;
    private String detailName;

    private List<ParameterDto> listDetail;

    public ParameterTypeDto() {
    }

    public ParameterTypeDto(String paramTypeCode, String paramTypeName, List<ParameterDto> listDetail) {
        this.paramTypeCode = paramTypeCode;
        this.paramTypeName = paramTypeName;
        this.listDetail = listDetail;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getParamTypeCode() {
        return paramTypeCode;
    }

    public void setParamTypeCode(String paramTypeCode) {
        this.paramTypeCode = paramTypeCode;
    }

    public String getParamTypeName() {
        return paramTypeName;
    }

    public void setParamTypeName(String paramTypeName) {
        this.paramTypeName = paramTypeName;
    }

    public List<ParameterDto> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<ParameterDto> listDetail) {
        this.listDetail = listDetail;
    }
}
