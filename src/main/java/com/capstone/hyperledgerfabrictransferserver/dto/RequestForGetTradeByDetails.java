package com.capstone.hyperledgerfabrictransferserver.dto;

import com.capstone.hyperledgerfabrictransferserver.domain.DateTimeRange;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestForGetTradeByDetails {
    @Getter
    private String senderIdentifier;

    @Getter
    private String receiverIdentifier;

    @Getter
    private DateTimeRange dateTimeRange;

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer hour;

    private Integer minute;

    private Integer second;

    @Getter
    private LocalDateTime fromLocalDateTime;

    @Getter
    private LocalDateTime untilLocalDateTime;

    @Builder
    public RequestForGetTradeByDetails(String senderIdentifier, String receiverIdentifier, DateTimeRange dateTimeRange, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        this.senderIdentifier = senderIdentifier;
        this.receiverIdentifier = receiverIdentifier;
        this.dateTimeRange = dateTimeRange;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        switch (dateTimeRange) {
            case YEAR:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        1,
                        1,
                        0,
                        0,
                        0
                );
                untilLocalDateTime = LocalDateTime.of(
                        year + 1,
                        1,
                        1,
                        0,
                        0,
                        0
                );
                break;
            case MONTH:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        1,
                        0,
                        0,
                        0
                );
                untilLocalDateTime = LocalDateTime.of(
                        year,
                        month + 1,
                        1,
                        0,
                        0,
                        0
                );
                break;
            case DAY:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        0,
                        0,
                        0
                );
                untilLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day + 1,
                        0,
                        0,
                        0
                );
                break;
            case HOUR:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour,
                        0,
                        0
                );
                untilLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour + 1,
                        0,
                        0
                );
                break;
            case MINUTE:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour,
                        minute,
                        0
                );
                untilLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour,
                        minute + 1,
                        0
                );
                break;
            case SECOND:
                fromLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour,
                        minute,
                        second
                );
                untilLocalDateTime = LocalDateTime.of(
                        year,
                        month,
                        day,
                        hour,
                        minute,
                        second + 1
                );
                break;
        }

    }
}
