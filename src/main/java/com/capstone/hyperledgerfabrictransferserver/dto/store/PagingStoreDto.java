package com.capstone.hyperledgerfabrictransferserver.dto.store;

import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import com.capstone.hyperledgerfabrictransferserver.dto.user.UserDto;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PagingStoreDto {

    private Long totalUserNumber;
    private int totalPage;
    private List<GetStoreResponse> storeResponseList;

    @Builder
    public PagingStoreDto(Long totalUserNumber, int totalPage, List<GetStoreResponse> storeResponseList) {
        this.totalUserNumber = totalUserNumber;
        this.totalPage = totalPage;
        this.storeResponseList = storeResponseList;
    }

    public static PagingStoreDto from(Page<Store> pagingStore) {
        return PagingStoreDto.builder()
                .totalUserNumber(pagingStore.getTotalElements())
                .totalPage(pagingStore.getTotalPages())
                .storeResponseList(
                        pagingStore.getContent().stream()
                                .map(store -> GetStoreResponse.from(store))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
