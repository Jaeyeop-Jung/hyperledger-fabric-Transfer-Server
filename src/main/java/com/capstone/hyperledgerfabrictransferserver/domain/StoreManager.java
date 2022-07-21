package com.capstone.hyperledgerfabrictransferserver.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreManager extends User{

    private String phoneNumber;

    private StoreManager(String phoneNumber, String password, UserRole userRole, String name) {
        super(password, userRole, name);
        this.phoneNumber = phoneNumber;
    }

    public static StoreManager of(String phoneNumber, String password, UserRole userRole, String name) {
        return new StoreManager(phoneNumber, password, userRole, name);
    }
}
