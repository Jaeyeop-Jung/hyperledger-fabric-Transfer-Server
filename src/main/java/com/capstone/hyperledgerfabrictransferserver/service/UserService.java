package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.dto.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.UserLoginResponse;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    public User getUserByJwtToken(HttpServletRequest httpServletRequest);

    public UserLoginResponse join(UserJoinRequest userJoinRequest);

    public UserLoginResponse login(HttpServletRequest httpServletRequest, UserLoginRequest userLoginRequest);

    public void changePassword(HttpServletRequest httpServletRequest, String newPassword);

    public void delete(HttpServletRequest httpServletRequest);

    public AssetDto getAsset(HttpServletRequest httpServletRequest);
}
