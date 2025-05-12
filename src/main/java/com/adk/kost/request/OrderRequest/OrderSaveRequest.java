package com.adk.kost.request.OrderRequest;



import java.util.List;

public class OrderSaveRequest { 

    private String orderName;
    private String orderStatus;
    private Integer totalPerson;
    private String noHp;

    private List<PersonRequest> listPerson;

    public OrderSaveRequest() {
    }

    public OrderSaveRequest(String orderName,  String paymentStatus, Integer totalPerson) {
        this.orderName = orderName;
        this.orderStatus = paymentStatus;
        this.totalPerson = totalPerson;
    }

    public void setTotalPerson(Integer totalPerson) {
        this.totalPerson = totalPerson;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setnoHp(String noHp) {
        this.noHp = noHp;
    }

    public List<PersonRequest> getListPerson() {
        return listPerson;
    }

    public void setListPerson(List<PersonRequest> listPerson) {
        this.listPerson = listPerson;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalPerson() {
        return totalPerson;
    }



}
