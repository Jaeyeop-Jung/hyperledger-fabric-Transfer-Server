package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SHOPIMAGE")
public class ShopImage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPIMAGE_ID")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "SHOP_ID")
    private Shop shop;

    @NotNull
    private String name;

    @NotNull
    private Long size;

    @NotNull
    private String extension;
}
