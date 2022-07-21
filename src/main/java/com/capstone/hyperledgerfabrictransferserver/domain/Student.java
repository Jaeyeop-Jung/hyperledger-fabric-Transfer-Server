package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends User{

    @NotNull
    private String studentNumber;

    private Student(String studentNumber, String password, UserRole userRole, String name) {
        super(password, userRole, name);
        this.studentNumber = studentNumber;
    }
    public static Student of(String studentNumber, String password, UserRole userRole, String name) {
        return new Student(studentNumber, password, userRole, name);
    }
}
