package com.adk.kost.request.roomRequest;

import java.util.List;

public class DeleteDetailRoomRequest {

    private List<DeleteRoomRequest> listDelete;

    public List<DeleteRoomRequest> getListDelete() {
        return listDelete;
    }

    public void setListDelete(List<DeleteRoomRequest> listDelete) {
        this.listDelete = listDelete;
    }
}
