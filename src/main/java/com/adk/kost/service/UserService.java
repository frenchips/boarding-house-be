package com.adk.kost.service;


import com.adk.kost.dto.UserDto;
import com.adk.kost.entity.Users;
import com.adk.kost.request.User;

public interface UserService {

    Users createAccount(User user);
    UserDto getLogin(User user);
    String getUsername(String username);
//    Users changePassword(User user);
//    String sendOtp();
}
