package com.adk.kost.util;

import com.adk.kost.constant.Constants;
import com.adk.kost.constant.ResponseStatus;
import com.adk.kost.request.BaseSearchRequest;
import com.adk.kost.response.Response;
import com.adk.kost.response.SearchResponse;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ResponseUtil{

    public <T> Response<T> generateResponseSuccess(String reason, T data){
        Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
        result.setMessage(reason);
        result.setData(data);

        return result;
    }

    public <T> Response <T> generateResponseSuccess(T data){
        return this.generateResponseSuccess(Constants.MESSAGE_RESPONSE_SUCCESS, data);
    }


    protected Pageable getPageable(Integer pageNo, Integer pageSize){
        return PageRequest.of(pageNo - 1, pageSize);
    }

    public ResponseEntity<SearchResponse> generateSearchResponse(BaseSearchRequest request, @NonNull List data){

        List<Object> list = data;
        Pageable pageable = getPageable(request.getPageNo(), request.getPageSize());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());

        Page<Object> page = null;
        List<Object> listNull = new ArrayList<>();
        SearchResponse response = null;
        if(start > end){
            response = new SearchResponse<>(ResponseStatus.SUCCESS, "1",
                    "Data berhasil ditampilkan", request.getPageNo(), request.getPageSize(), 0, 0L,0 , listNull);

        } else {
            page =  new PageImpl<>(list.subList(start, end), pageable, list.size());
            response = new SearchResponse<>(ResponseStatus.SUCCESS, "1",
                    "Data berhasil ditampilkan", request.getPageNo(), request.getPageSize(), page.getSize(), page.getTotalElements(), page.getTotalPages(),  page.toList());

        }



        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
