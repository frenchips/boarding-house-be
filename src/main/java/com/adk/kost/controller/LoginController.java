package com.adk.kost.controller;

import com.adk.kost.dto.UserDto;
import com.adk.kost.entity.Users;
import com.adk.kost.request.User;
import com.adk.kost.response.Response;
import com.adk.kost.service.UserService;
import com.adk.kost.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    ResponseUtil resp;

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Users> addAccount(@RequestBody User request){

        Users result = userService.createAccount(request);

        return resp.generateResponseSuccess(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<UserDto> loginAccount(@RequestBody User request){

        UserDto result = userService.getLogin(request);

        return resp.generateResponseSuccess(result);
    }


    @RequestMapping(value = "/getUsername", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> getUsername(@RequestParam String username){

        return resp.generateResponseSuccess(userService.getUsername(username));
    }

//
//    @RequestMapping(value = "/sendOtp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response<Object> getOtp(){
//
//        return resp.generateResponseSuccess(userService.sendOtp());
//    }

}
