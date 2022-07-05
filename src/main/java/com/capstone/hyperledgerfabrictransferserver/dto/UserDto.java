package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class UserDto {

    private Long studentId;

    private String password;

    private String name;

    private HashMap<String, String> coin;

    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;

    @Builder
    public UserDto(Long studentId, String password, String name, HashMap<String, String> coin, LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.coin = coin;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }
}
