package com.capstone.hyperledgerfabrictransferserver.dto.coin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class DailyCoinTradingVolume {

    private Date tradingDate;
    private Long tradingVolume;

    public DailyCoinTradingVolume(Date tradingDate, Long tradingVolume) {
        this.tradingDate = tradingDate;
        this.tradingVolume = tradingVolume;
    }
}
