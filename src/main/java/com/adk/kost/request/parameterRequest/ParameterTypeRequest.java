package com.adk.kost.request.parameterRequest;

import java.util.List;

public class ParameterTypeRequest {

    private String paramTypeCode;
    private String paramTypeName;
    private List<ParameterRequest> parameterRequestList;

    public ParameterTypeRequest(){

    }

    public ParameterTypeRequest(String paramTypeCode, String paramTypeName, List<ParameterRequest> parameterRequestList){
        this.paramTypeCode = paramTypeCode;
        this.paramTypeName = paramTypeName;
        this.parameterRequestList = parameterRequestList;
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

    public List<ParameterRequest> getParameterRequestList() {
        return parameterRequestList;
    }

    public void setParameterRequestList(List<ParameterRequest> parameterRequestList) {
        this.parameterRequestList = parameterRequestList;
    }
}
