package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserDto {

    private String identifier;

    private String password;

    private String name;

    private HashMap<String, String> coin;

    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;

    @Builder
    public UserDto(String identifier, String password, String name, HashMap<String, String> coin, LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
        this.coin = coin;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }
}
