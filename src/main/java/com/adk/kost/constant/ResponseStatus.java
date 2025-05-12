package com.adk.kost.constant;

public enum ResponseStatus {
    SUCCESS("success"), ERROR("error");

    private String val;

    ResponseStatus(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }
}
