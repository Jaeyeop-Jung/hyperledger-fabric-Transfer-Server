package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;
import org.apache.http.impl.bootstrap.HttpServer;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public UserLoginResponse join(UserJoinRequest userJoinRequest);

    public UserLoginResponse login(HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest);
}
