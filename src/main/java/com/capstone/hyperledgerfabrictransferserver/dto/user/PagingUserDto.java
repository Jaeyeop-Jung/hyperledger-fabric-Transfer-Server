package com.capstone.hyperledgerfabrictransferserver.dto.user;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingUserDto {

    private Long totalUserNumber;

    private int totalPage;
    private List<UserDto> userDtoList;

    @Builder
    public PagingUserDto(Long totalUserNumber, int totalPage, List<UserDto> userDtoList) {
        this.totalUserNumber = totalUserNumber;
        this.totalPage = totalPage;
        this.userDtoList = userDtoList;
    }

    public static PagingUserDto from(Page<User> users) {
        return PagingUserDto.builder()
                .totalPage(users.getTotalPages())
                .totalUserNumber(users.getTotalElements())
                .userDtoList(
                        users.getContent().stream()
                                .map(user -> UserDto.from(user))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
