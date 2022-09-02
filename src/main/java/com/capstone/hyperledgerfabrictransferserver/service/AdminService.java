package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.NotExistsAdminException;
import com.capstone.hyperledgerfabrictransferserver.domain.Admin;
import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.filter.JwtTokenProvider;
import com.capstone.hyperledgerfabrictransferserver.repository.AdminRepository;
import com.capstone.hyperledgerfabrictransferserver.util.Sha256Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

     private final AdminRepository adminRepository;
     private final JwtTokenProvider jwtTokenProvider;

     public AdminLoginResponse login(AdminLoginRequest adminLoginRequest) {
         Admin findAdmin = adminRepository.findByEmailAndPassword(
                 adminLoginRequest.getEmail(),
                 Sha256Util.digest(adminLoginRequest.getPassword())
         ).orElseThrow(() -> new NotExistsAdminException("존재하지 않은 관리자입니다"));

         return AdminLoginResponse.builder()
                 .accessToken(jwtTokenProvider.generateJwtToken(findAdmin))
                 .build();
     }
}
