package com.capstone.hyperledgerfabrictransferserver.api;


import com.capstone.hyperledgerfabrictransferserver.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreApiController {

    private final StoreService storeService;

}
