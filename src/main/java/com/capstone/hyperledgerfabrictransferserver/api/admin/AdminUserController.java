package com.capstone.hyperledgerfabrictransferserver.api.admin;

import com.capstone.hyperledgerfabrictransferserver.dto.user.AssetDto;
import com.capstone.hyperledgerfabrictransferserver.dto.user.ForceDeleteUserRequest;
import com.capstone.hyperledgerfabrictransferserver.dto.user.PagingUserDto;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserModifyRequest;
import com.capstone.hyperledgerfabrictransferserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<PagingUserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(userService.getAllUser(page));
    }

    @GetMapping("/user")
    public ResponseEntity<AssetDto> getUser(@RequestParam String identifier) {
        return ResponseEntity.ok(userService.getAsset(identifier));
    }

    @PutMapping("/user")
    public ResponseEntity<Void> modifyUserInfo(@RequestBody UserModifyRequest userModifyRequest) {
        userService.modifyUserInfo(userModifyRequest);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> forceDeleteUser(@RequestBody ForceDeleteUserRequest forceDeleteUserRequest) {
        userService.delete(forceDeleteUserRequest);
        return ResponseEntity.ok(null);
    }

}
