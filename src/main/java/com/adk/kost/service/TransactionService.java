package com.adk.kost.service;

import com.adk.kost.dto.BookingDto;
import com.adk.kost.dto.OrderDto;
import com.adk.kost.dto.SubmissionDto;
import com.adk.kost.entity.Order;
import com.adk.kost.entity.Payment;
import com.adk.kost.request.OrderRequest.HomeOrderRequest;
import com.adk.kost.request.OrderRequest.OrderSaveRequest;
import com.adk.kost.request.OrderRequest.PaymentRequest;
import com.adk.kost.request.OrderRequest.SubmissionRequest;

import java.util.List;


public interface TransactionService {
    Order saveOrder(OrderSaveRequest order, String no) ;
    Payment savePayment(PaymentRequest request);
    Order loadOrder(String no);
    List<OrderDto> findByRoomNoAndOrderStatus(HomeOrderRequest request);
    List<BookingDto> findByRoomNoAndOrder(HomeOrderRequest request);
    BookingDto findByRoomNoTransaction(String no);
    List<SubmissionDto> findSubmissionOrder(SubmissionRequest request);
}
