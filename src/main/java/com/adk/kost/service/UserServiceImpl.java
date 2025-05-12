package com.adk.kost.service;


import com.adk.kost.dto.UserDto;
import com.adk.kost.entity.Users;
import com.adk.kost.exception.ValidateException;
import com.adk.kost.repository.UserRepository;
import com.adk.kost.request.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public Users createAccount(User user) {


        Users users = null;

        if(findUsername(user.getUsername()) != null){
            throw new ValidateException("Username sudah terdaftar");
        } else {
            users.setUsername(user.getUsername());
            users.setPassword(user.getPassword());
            users.setUserType(user.getUserType());
            this.createAudit(users, user.getUsername());
        }
        return userRepository.save(users);
    }


    public UserDto getLogin(User user) {

        List<Object[]> data = userRepository.getUser(user.getUsername(), user.getPassword(), user.getUserType());
        UserDto dto = new UserDto();
        for(Object[] obj : data){
            dto.setUsername(obj[0] != null ? (String) obj[0] : null);
            dto.setPassword(obj[1] != null ? (String) obj[1] : null);
            dto.setUserType(obj[2] != null ? (String) obj[2] : null);
        }
        return dto;
    }

    @Override
    public String getUsername(String username) {
        UUID val = userRepository.getUser(username);
        Users users = userRepository.findById(val).orElse(null);
        boolean check = users != null;
        if(!check) throw new ValidateException("Username tidak terdaftar");
        return null;
    }


    private String findUsername(String username){
        UUID val = userRepository.getUser(username);
        Users users = userRepository.findById(val).orElse(null);
        return users.getUsername();
    }



//    @Override
//    public Users changePassword(User user) {
//        UUID val = userRepository.getUser(user.getUsername());
//
//        Users users = userRepository.findById(val).orElse(null);
//
//        if(users != null){
//            ValidateUtil.messageRoom(user);
//
//            users.setPassword(AES256Util.generateEncrypted(user.getPassword()));
//            users.setUserType(Constants.CS);
//            this.setUpdateAudit(users, users.getUsername());
//        } else {
//            throw new ValidationException("Username tidak terdaftar");
//        }
//        return users;
//    }

//    @Override
//    public String sendOtp() {
//        return generateOtp();
//    }

//    private String generateOtp(){
//        Random secureRandom = new Random();
//
//        int boundedRandom = 1000 + secureRandom.nextInt(9000);
//        String random = String.valueOf(boundedRandom);
//        return random;
//    }
}
