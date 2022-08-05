package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "delete is not true")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COIN_ID")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long issuance;

    @NotNull
    @Column(name = "DELETED")
    private boolean deleted;

    public static Coin of(String coinName, Long issuance){
        return new Coin(coinName,  issuance,false);
    }

    public Coin(String name, Long issuance, boolean deleted) {
        this.name = name;
        this.issuance = issuance;
        this.deleted = deleted;
    }

    public void setDeleted(){
        this.name = name + id;
        this.deleted = true;
    }

    public void modifyIssuance(Long issuance){
        this.issuance = issuance;
    }
}
