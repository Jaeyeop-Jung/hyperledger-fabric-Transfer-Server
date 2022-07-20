package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STOREIMAGE")
public class StoreImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STOREIMAGE_ID")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @NotNull
    private String name;

    @NotNull
    private Long size;

    @NotNull
    private String extension;
}
