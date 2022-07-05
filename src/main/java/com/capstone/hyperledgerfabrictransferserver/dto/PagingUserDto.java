package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingUserDto {

    private List<UserDto> userDtoList;

    private Long totalUserNumber;

    private int totalPage;

    @Builder
    public PagingUserDto(List<User> userDtoList, Long totalUserNumber, int totalPage) {
        this.userDtoList = userListToDtoList(userDtoList);
        this.totalUserNumber = totalUserNumber;
        this.totalPage = totalPage;
    }

    private List<UserDto> userListToDtoList(List<User> userList) {
        return userList.stream()
                .map(user -> UserDto.builder()
                        .studentId(user.getStudentId())
                        .name(user.getName())
                        .dateCreated(user.getDateCreated())
                        .lastUpdated(user.getLastUpdated())
                        .build())
                .collect(Collectors.toList());
    }
}
