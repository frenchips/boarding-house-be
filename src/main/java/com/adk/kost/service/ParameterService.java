package com.adk.kost.service;

import com.adk.kost.dto.ParameterDto;
import com.adk.kost.dto.ParameterTypeDto;
import com.adk.kost.entity.ParameterType;
import com.adk.kost.request.parameterRequest.DeleteParameterRequest;
import com.adk.kost.request.parameterRequest.ParameterTypeRequest;
import com.adk.kost.request.parameterRequest.SearchParameterRequest;

import java.util.List;

public interface ParameterService {
    ParameterType submit(ParameterTypeRequest request);
    List<ParameterTypeDto> search(SearchParameterRequest dto);
    ParameterType delete(DeleteParameterRequest request);
    ParameterDto getByParameter(String code);
    List<ParameterDto> getAllByParameter();
    List<ParameterDto> getAllByAmtPerson();
    List<ParameterDto> getAllByGender();
}
