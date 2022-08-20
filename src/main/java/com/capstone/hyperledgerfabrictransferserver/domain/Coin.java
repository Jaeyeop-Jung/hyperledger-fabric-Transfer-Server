package com.capstone.hyperledgerfabrictransferserver.domain;

import com.capstone.hyperledgerfabrictransferserver.util.RandomGenerateUtil;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.security.SecureRandom;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted != true")
@SQLDelete(sql = "update Coin set DELETED = true, name = name + id")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COIN_ID")
    private Long id;

    @NotNull
    @Column(unique = true)
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
        this.name = name + "#" + RandomGenerateUtil.generate(4);
        this.deleted = true;
    }

    public void modifyIssuance(Long issuance){
        this.issuance = issuance;
    }
}
