package com.adk.kost.controller;



import com.adk.kost.dto.ParameterDto;
import com.adk.kost.dto.ParameterTypeDto;
import com.adk.kost.entity.ParameterType;
import com.adk.kost.request.parameterRequest.DeleteParameterRequest;
import com.adk.kost.request.parameterRequest.ParameterTypeRequest;
import com.adk.kost.request.parameterRequest.SearchParameterRequest;
import com.adk.kost.response.Response;
import com.adk.kost.response.SearchResponse;
import com.adk.kost.service.ParameterServiceImpl;
import com.adk.kost.util.ResponseUtil;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class ParameterController {

    @Autowired
    ParameterServiceImpl parameterService;

    @Autowired
    ResponseUtil resp;

    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ParameterType> addParameter(@RequestBody ParameterTypeRequest request){

        ParameterType result = parameterService.submit(request);

        return resp.generateResponseSuccess(result);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchParameterRequest request){

        List<ParameterTypeDto> listData = parameterService.search(request);

        return resp.generateSearchResponse(request, listData);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ParameterType> delete(@RequestBody DeleteParameterRequest request){

         ParameterType data = parameterService.delete(request);

        return resp.generateResponseSuccess(data);
    }


    @RequestMapping(value = "/getByParameter/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<ParameterDto> getByParameterByCode(@PathVariable("id") String id){

        ParameterDto data = parameterService.getByParameter(id);

        return resp.generateResponseSuccess(data);
    }


    @RequestMapping(value = "/getAllByParameterByPay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ParameterDto>> getByParameterByCode(){

        List<ParameterDto> data = parameterService.getAllByParameter();

        return resp.generateResponseSuccess(data);
    }


    @RequestMapping(value = "/getAllByParameterByAmtPerson", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ParameterDto>> getByAmtPerson(){

        List<ParameterDto> result = parameterService.getAllByAmtPerson();

        return resp.generateResponseSuccess(result);
    }


    @RequestMapping(value = "/getAllByParameterByGender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<ParameterDto>> getByGender(){

        List<ParameterDto> result = parameterService.getAllByGender();

        return resp.generateResponseSuccess(result);
    }





}
