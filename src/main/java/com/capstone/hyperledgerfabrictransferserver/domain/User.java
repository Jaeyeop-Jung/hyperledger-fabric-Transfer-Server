package com.capstone.hyperledgerfabrictransferserver.domain;

import com.capstone.hyperledgerfabrictransferserver.dto.user.UserModifyRequest;
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

    public void modifyPassword(String password){
        this.password = password;
    }

    public void modifyIdentifier(String identifier) {this.identifier = identifier;}

    public void changeUserRole(UserRole userRole) {this.userRole = userRole;}

    public void modifyName(String name) {this.name = name;}
}
