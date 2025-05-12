package com.adk.kost.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

public enum MessageStatus {
    STATUS_SUCCESS("Data retrieved successfully");

    private String val;

    MessageStatus(String val){
        this.val = val;
    }

    @JsonValue
    public String getVal(){
        return val;
    }

    @Override
    public String toString(){
        return val;
    }


}
