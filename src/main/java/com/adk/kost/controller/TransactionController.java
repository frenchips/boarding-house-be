package com.adk.kost.controller;


import com.adk.kost.dto.BookingDto;
import com.adk.kost.dto.OrderDto;
import com.adk.kost.entity.Order;
import com.adk.kost.entity.Payment;
import com.adk.kost.request.OrderRequest.HomeOrderRequest;
import com.adk.kost.request.OrderRequest.OrderSaveRequest;
import com.adk.kost.request.OrderRequest.PaymentRequest;
import com.adk.kost.response.Response;
import com.adk.kost.response.SearchResponse;
import com.adk.kost.service.CommonService;
import com.adk.kost.service.TransactionService;
import com.adk.kost.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/adk")
@CrossOrigin
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    CommonService whatsAppService;

    @Autowired
    ResponseUtil resp;

    @RequestMapping(value = "/saveOrder/{no}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Order> save(@RequestBody OrderSaveRequest request, @PathVariable("no") String no)  {

        Order result = transactionService.saveOrder(request, no);

        return resp.generateResponseSuccess(result);
    }


    @RequestMapping(value = "/savePayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Payment> save(@RequestBody PaymentRequest request)  {

        Payment result = transactionService.savePayment(request);

        return resp.generateResponseSuccess(result);
    }


    @RequestMapping(value = "/getOrder/{no}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Order> loadOrder(@PathVariable("no") String no)  {

        Order result = transactionService.loadOrder(no);

        return resp.generateResponseSuccess(result);
    }

    @RequestMapping(value = "/findOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponse> findOrder(@RequestBody HomeOrderRequest request)  {

        List<OrderDto> result = transactionService.findByRoomNoAndOrderStatus(request);
        return resp.generateSearchResponse(request, result);
    }

    @RequestMapping(value = "/findTransaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SearchResponse>  findTransaction(@RequestBody HomeOrderRequest request)  {

        List<BookingDto> result = transactionService.findByRoomNoAndOrder(request);
        return resp.generateSearchResponse(request, result);
    }


    @RequestMapping(value = "/findDtlTransaction/{no}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<BookingDto>  findDetailTransaction(@PathVariable("no") String no)  {

        BookingDto result = transactionService.findByRoomNoTransaction(no);
        return resp.generateResponseSuccess(result);
    }

    @RequestMapping(value = "/sendMessageWa", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> sendMessage(@RequestParam String phone, @RequestParam String message)  {
        return resp.generateResponseSuccess(whatsAppService.sendMessageWa(phone, message));
    }
}
