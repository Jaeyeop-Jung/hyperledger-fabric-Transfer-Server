package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotNull
    private String identifier;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotNull
    private String name;

    private User(String identifier, String password, UserRole userRole, String name) {
        this.identifier = identifier;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }

    public static User of(String identifier, String password, UserRole userRole, String name) {
        return new User(identifier, password, userRole, name);
    }

    public void changePassword(String password){
        this.password = password;
    }
}
