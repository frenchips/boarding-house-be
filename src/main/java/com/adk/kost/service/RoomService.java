package com.adk.kost.service;

import com.adk.kost.dto.RoomDto;
import com.adk.kost.entity.Room;
import com.adk.kost.request.roomRequest.DeleteDetailRoomRequest;
import com.adk.kost.request.roomRequest.RoomRequest;
import com.adk.kost.request.roomRequest.SearchRoomRequest;


import java.util.List;

public interface RoomService {

    Room create (RoomRequest request);
    Room delete (String id);
    List<RoomDto> search (SearchRoomRequest request);
    Long byId (String no);
    RoomDto byRoomAndPrice (String no);
    void deleteDetail(DeleteDetailRoomRequest request);
    Room getLoaded(String no);
}
