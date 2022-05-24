package com.capstone.hyperledgerfabrictransferserver.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TRADE")
public class Trade extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRADE_ID")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVED_USER_ID")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVED_SHOP_ID")
    private Shop shop;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COIN_ID")
    private Coin coin;

    @NotNull
    private Long amount;

    public static Trade of(User sender, User receiver, Coin coin, Long amount){
        return new Trade(sender, receiver, coin, amount);
    }

    public static Trade of(User sender, Shop shop, Coin coin, Long amount){
        return new Trade(sender, shop, coin, amount);
    }

    private Trade(User sender, User receiver, Coin coin, Long amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.coin = coin;
        this.amount = amount;
    }

    public Trade(User sender, Shop shop, Coin coin, Long amount) {
        this.sender = sender;
        this.shop = shop;
        this.coin = coin;
        this.amount = amount;
    }

}
