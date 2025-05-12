package com.adk.kost.request.OrderRequest;

public class PaymentRequest {

    private String orderName;
    private Integer priceRoom;



    public Integer getPriceRoom() {
        return priceRoom;
    }

    public void setPriceRoom(Integer priceRoom) {
        this.priceRoom = priceRoom;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

}
