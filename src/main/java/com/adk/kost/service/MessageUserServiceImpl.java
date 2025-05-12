package com.adk.kost.service;


import com.adk.kost.constant.Constants;
import com.adk.kost.exception.ValidateException;
import com.adk.kost.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageUserServiceImpl implements MessageUserService{

    @Autowired
    private ParameterRepository parameterRepository;

    @Override
    public String messageUser(String user, String pass, Integer nomor1, Integer nomor2, String to) {

        String sendUser = parameterRepository.getParamCodeByDescription(Constants.PAYLOAD_USER);

        if(!sendUser.isEmpty()){
            return String.format(sendUser, user, pass, nomor1, nomor2, to);
        } else {
            throw new ValidateException("Maaf pesan whatsapp tidak ada");
        }
    }
}
