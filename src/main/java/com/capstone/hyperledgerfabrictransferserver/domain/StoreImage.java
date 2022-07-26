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
    private String name;

    @NotNull
    private Long size;

    @NotNull
    private ImageFileExtension imageFileExtension;

    public StoreImage(String name, Long size, ImageFileExtension imageFileExtension) {
        this.name = name;
        this.size = size;
        this.imageFileExtension = imageFileExtension;
    }

    public static StoreImage of(String name, Long size, ImageFileExtension imageFileExtension) {
        return new StoreImage(name, size, imageFileExtension);
    }
}
