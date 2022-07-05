package com.capstone.hyperledgerfabrictransferserver.dto;

import lombok.*;

import java.util.HashMap;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserDto {

    private Long studentId;

    private String password;

    private String name;

    private HashMap<String, String> coin;

    @Builder
    public UserDto(Long studentId, String password, String name, HashMap<String, String> coin) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.coin = coin;
    }
}
