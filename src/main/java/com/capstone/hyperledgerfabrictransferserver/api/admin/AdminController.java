package com.capstone.hyperledgerfabrictransferserver.api.admin;

import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.admin.AdminLoginResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.user.*;
import com.capstone.hyperledgerfabrictransferserver.service.AdminService;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(
        @ModelAttribute AdminLoginRequest adminLoginRequest
    ){
        return ResponseEntity.ok(adminService.login(adminLoginRequest));
    }

}
