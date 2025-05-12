package com.adk.kost.service;


import com.adk.kost.dto.ParameterDto;
import com.adk.kost.dto.ParameterTypeDto;
import com.adk.kost.entity.Parameter;
import com.adk.kost.entity.ParameterType;
import com.adk.kost.repository.ParameterRepository;
import com.adk.kost.repository.ParameterTypeRepository;
import com.adk.kost.request.parameterRequest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterServiceImpl extends BaseServiceImpl implements ParameterService{

    @Autowired
    ParameterRepository parameterDAOMap;

    @Autowired
    ParameterTypeRepository parameterTypeDAOMap;

    @Override
    public ParameterType submit(ParameterTypeRequest request) {


        ParameterType paramOld = parameterTypeDAOMap.findById(request.getParamTypeCode()).orElse(null);

        List<Parameter> parameterList = new ArrayList<>();

        if(paramOld != null){

            paramOld.setParamTypeName(request.getParamTypeName());

            if(!request.getParameterRequestList().isEmpty()){
                for(int i = 0; i < request.getParameterRequestList().size(); i++){
                    ParameterRequest requestOld = request.getParameterRequestList().get(i);
                    parameterList.add(addParameterDetail(paramOld, requestOld));
                }
            }

            paramOld.getParameterList().clear();
            paramOld.getParameterList().addAll(parameterList);
            this.updateAudit(paramOld, "Admin");

            return parameterTypeDAOMap.save(paramOld);

        } else {
            ParameterType  paramNew = new ParameterType();

            paramNew.setParamTypeCd(request.getParamTypeCode());
            paramNew.setParamTypeName(request.getParamTypeName());

            if(!request.getParameterRequestList().isEmpty()){
                for(int i = 0; i < request.getParameterRequestList().size(); i++){
                    ParameterRequest paramReq =  request.getParameterRequestList().get(i);
                    parameterList.add(addParameterDetail(paramNew, paramReq));
                }
            }

            this.createAudit(paramNew, "Admin");
            paramNew.setParameterList(parameterList);

            return parameterTypeDAOMap.save(paramNew);

        }

    }


    public Parameter addParameterDetail(ParameterType param, ParameterRequest request){


        Parameter parameterDtlOld = parameterDAOMap.findById(request.getParamCd()).orElse(null);

        if(parameterDtlOld != null){

            parameterDtlOld.setParamType(param);
            parameterDtlOld.setParamName(request.getParamName());
            parameterDtlOld.setDescription(request.getDescription());
            this.updateAudit(parameterDtlOld, "Admin");

            return parameterDtlOld;
        } else {

            Parameter paramDetail = new Parameter();
            paramDetail.setParamCd(request.getParamCd());
            paramDetail.setParamType(param);
            paramDetail.setParamName(request.getParamName());
            paramDetail.setDescription(request.getDescription());

            this.createAudit(paramDetail, "Admin");
            return paramDetail;
        }
    }


    @Override
    public List<ParameterTypeDto> search(SearchParameterRequest request) {

        List<Object[]> listData = parameterTypeDAOMap.findAllParameter(request.getSearchVal());

        List<ParameterTypeDto> listDto = new ArrayList<>();

        for(Object[] obj : listData){
            ParameterTypeDto dto = new ParameterTypeDto();

            dto.setParamTypeCode(obj[0] != null ? (String) obj[0] : null);
            dto.setParamTypeName(obj[1] != null ? (String) obj[1] : null);
            dto.setDetailName(obj[2] != null ? (String) obj[2] : null);

            List<Object[]> listData2 = parameterDAOMap.findAllParameterDetail((String) obj[0]);

            List<ParameterDto> listDto2 = new ArrayList<>();
            for(Object[] obj2 : listData2){
                ParameterDto dto2 = new ParameterDto();
                dto2.setParamCode(obj2[0] != null ? (String) obj2[0] : null);
                dto2.setParamName(obj2[1] != null ?(String) obj2[1] : null);
                dto2.setDescription(obj2[2] != null ? (String) obj2[2] : null);

                listDto2.add(dto2);

            }

            dto.setListDetail(listDto2);

            listDto.add(dto);
        }

        return listDto;
    }

    @Override
    public ParameterType delete(DeleteParameterRequest request) {

        ParameterType data = null;
        if(!request.getDeleteRequest().isEmpty()){

            for(int i = 0; i < request.getDeleteRequest().size(); i++){
                DeleteRequest req = request.getDeleteRequest().get(i);

                data  = parameterTypeDAOMap.findById(req.getCode()).orElse(null);

                if(data != null){
                    this.setDeleteAudit(data, "Admin");
                    parameterTypeDAOMap.save(data);
                }
            }
        }

        return data;
    }

    @Override
    public ParameterDto getByParameter(String code) {
        Object[] obj = parameterDAOMap.getByParameterByCode(code);
        ParameterDto dto = new ParameterDto();
        dto.setParamCode(obj[0] != null ? (String) obj[0] : null);
        dto.setParamName(obj[1] != null ? (String) obj[1] : null);
        dto.setDescription(obj[2] != null ? (String)  obj[2] : null);

        return dto;
    }

    @Override
    public List<ParameterDto> getAllByParameter() {
        List<Object[]> listObj = parameterDAOMap.getAllByParameterByPay();
        List<ParameterDto> listDto = new ArrayList<>();
        for(Object[] obj : listObj){
            ParameterDto dto = new ParameterDto();
            dto.setParamName(obj[0] != null ? (String) obj[0] : null);
            dto.setDescription(obj[1] != null ? (String) obj[1] : null);

            listDto.add(dto);
        }

        return listDto;
    }

    @Override
    public List<ParameterDto> getAllByAmtPerson() {
        List<Object[]> listObj = parameterDAOMap.getAllByParameterByAmtPerson();
        List<ParameterDto> listDto = new ArrayList<>();
        for(Object[] obj : listObj){
            ParameterDto dto = new ParameterDto();
            dto.setParamName(obj[0] != null ? (String) obj[0] : null);
            dto.setDescription(obj[1] != null ? (String) obj[1] : null);

            listDto.add(dto);
        }

        return listDto;
    }


    @Override
    public List<ParameterDto> getAllByGender() {
        List<Object[]> listObj = parameterDAOMap.getAllByParameterByGender();
        List<ParameterDto> listDto = new ArrayList<>();
        for(Object[] obj : listObj){
            ParameterDto dto = new ParameterDto();
            dto.setParamName(obj[0] != null ? (String) obj[0] : null);
            dto.setDescription(obj[1] != null ? (String) obj[1] : null);

            listDto.add(dto);
        }

        return listDto;
    }


}
