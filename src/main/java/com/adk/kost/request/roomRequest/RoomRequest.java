package com.adk.kost.request.roomRequest;

public class RoomRequest {

    private String roomNo;
    private Integer price;

    RoomRequest(){

    }


    RoomRequest(String roomNo, Integer price){
        this.roomNo = roomNo;
        this.price = price;
    }

    RoomRequest(Integer price){
        this.price = price;
    }


    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
