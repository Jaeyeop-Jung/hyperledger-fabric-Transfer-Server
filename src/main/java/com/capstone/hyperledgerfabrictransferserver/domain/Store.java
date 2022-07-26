package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOREIMAGE_ID")
    private StoreImage storeImage;

    private Store(String name, String phoneNumber, String address, StoreImage storeImage) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.storeImage = storeImage;
    }

    public static Store of(String name, String phoneNumber, String address, StoreImage storeImage) {
        return new Store(name, phoneNumber, address, storeImage);
    }
}
