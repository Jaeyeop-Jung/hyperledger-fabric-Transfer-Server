package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @NotNull
    @CreatedDate
    private LocalDateTime dateCreated;

    @NotNull
    @LastModifiedDate
    private LocalDateTime lastUpdated;

}