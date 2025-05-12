package com.adk.kost.request.parameterRequest;

import java.util.List;

public class DeleteParameterRequest {

    private List<DeleteRequest> deleteRequest;

    public List<DeleteRequest> getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(List<DeleteRequest> deleteRequest) {
        this.deleteRequest = deleteRequest;
    }
}
