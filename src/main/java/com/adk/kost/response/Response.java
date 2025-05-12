package com.adk.kost.response;


import com.adk.kost.constant.Constants;
import com.adk.kost.constant.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class Response<T> {

    @NonNull
    private ResponseStatus status;
    private String message;

    @NonNull
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private Date prosesFinishDate;
    private T data;

    public Response() {
    }

    public Response(ResponseStatus status, Date prosesFinishDate) {
        this.status = status;
        this.prosesFinishDate = prosesFinishDate;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getProsesFinishDate() {
        return prosesFinishDate;
    }

    public void setProsesFinishDate(Date prosesFinishDate) {
        this.prosesFinishDate = prosesFinishDate;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
