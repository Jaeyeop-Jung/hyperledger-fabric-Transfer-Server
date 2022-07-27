package com.capstone.hyperledgerfabrictransferserver.dto.user;

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

    private String name;

    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;

    @Builder
    public UserDto(String identifier, String name, LocalDateTime dateCreated, LocalDateTime lastUpdated) {
        this.identifier = identifier;
        this.name = name;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .identifier(user.getIdentifier())
                .name(user.getName())
                .dateCreated(user.getDateCreated())
                .lastUpdated(user.getLastUpdated())
                .build();
    }
}
