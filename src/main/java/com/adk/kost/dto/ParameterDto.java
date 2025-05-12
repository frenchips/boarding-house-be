package com.adk.kost.dto;

public class ParameterDto {

    private String paramCode;
    private String paramType;
    private String paramName;
    private String description;

    public ParameterDto() {
    }

    public ParameterDto(String paramCode, String paramType, String paramName, String description) {
        this.paramCode = paramCode;
        this.paramType = paramType;
        this.paramName = paramName;
        this.description = description;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
