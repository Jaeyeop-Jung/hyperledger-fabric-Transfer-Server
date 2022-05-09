package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "STUDENT_ID"))
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotNull
    @Column(name = "STUDENT_ID")
    private Long studentId;

    @NotNull
    private String password;

    @NotNull
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @NotNull
    private String name;

    public User(Long studentId, String password, UserRole userRole, String name) {
        this.studentId = studentId;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }

    public static User of(Long studentId, String password, UserRole userRole, String name){
        return new User(studentId, password, userRole, name);
    }

    public void changePassword(String password){
        this.password = password;
    }
}
