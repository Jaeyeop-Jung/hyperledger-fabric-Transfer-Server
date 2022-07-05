package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    public User getUserByJwtToken(HttpServletRequest httpServletRequest);

    public UserLoginResponse join(UserJoinRequest userJoinRequest);

    public UserLoginResponse login(UserLoginRequest userLoginRequest);

    public void changePassword(HttpServletRequest httpServletRequest, String newPassword);

    public void delete(HttpServletRequest httpServletRequest);

    public AssetDto getAsset(HttpServletRequest httpServletRequest);

    public List<UserDto> getAllUser(int page);
}
