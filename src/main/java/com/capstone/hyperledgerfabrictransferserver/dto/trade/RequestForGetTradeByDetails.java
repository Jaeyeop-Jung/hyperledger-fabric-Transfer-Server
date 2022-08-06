package com.capstone.hyperledgerfabrictransferserver.dto.trade;

import com.capstone.hyperledgerfabrictransferserver.domain.DateTimeRange;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Calendar;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestForGetTradeByDetails {
    @Getter
    private String senderIdentifier;

    @Getter
    private String receiverIdentifier;

    @Getter
    private DateTimeRange dateTimeRange;
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromLocalDateTime;

    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime untilLocalDateTime;

    @Builder
    public RequestForGetTradeByDetails(String senderIdentifier, String receiverIdentifier, DateTimeRange dateTimeRange, LocalDateTime fromLocalDateTime, LocalDateTime untilLocalDateTime) {
        this.senderIdentifier = senderIdentifier;
        this.receiverIdentifier = receiverIdentifier;
        this.dateTimeRange = dateTimeRange;
        switch (dateTimeRange) {
            case YEAR:
                this.fromLocalDateTime = LocalDateTime.of(fromLocalDateTime.getYear(), 1,1,0,0,0);
                this.untilLocalDateTime = LocalDateTime.of(untilLocalDateTime.getYear(),12,31,23,59,59);
                break;
            case MONTH:
                this.fromLocalDateTime = LocalDateTime.of(fromLocalDateTime.getYear(), fromLocalDateTime.getMonthValue(), 1,0,0,0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, untilLocalDateTime.getMonthValue() - 1);
                this.untilLocalDateTime = LocalDateTime.of(untilLocalDateTime.getYear(), untilLocalDateTime.getMonthValue(), calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23,59,59);
                break;
            case DAY:
                this.fromLocalDateTime = LocalDateTime.of(fromLocalDateTime.getYear(), fromLocalDateTime.getMonthValue(), fromLocalDateTime.getDayOfMonth(), 0,0,0);
                this.untilLocalDateTime = LocalDateTime.of(untilLocalDateTime.getYear(), untilLocalDateTime.getMonthValue(), untilLocalDateTime.getDayOfMonth(), 23,59,59);
                break;
            case HOUR:
                this.fromLocalDateTime = LocalDateTime.of(fromLocalDateTime.getYear(), fromLocalDateTime.getMonthValue(), fromLocalDateTime.getDayOfMonth(), fromLocalDateTime.getHour(), 0, 0);
                this.untilLocalDateTime = LocalDateTime.of(untilLocalDateTime.getYear(), untilLocalDateTime.getMonthValue(), untilLocalDateTime.getDayOfMonth(), untilLocalDateTime.getHour(), 59, 9);
                break;
            case MINUTE:
                this.fromLocalDateTime = LocalDateTime.of(fromLocalDateTime.getYear(), fromLocalDateTime.getMonthValue(), fromLocalDateTime.getDayOfMonth(), fromLocalDateTime.getHour(), fromLocalDateTime.getMinute(), 0);
                this.untilLocalDateTime = LocalDateTime.of(untilLocalDateTime.getYear(), untilLocalDateTime.getMonthValue(), untilLocalDateTime.getDayOfMonth(), untilLocalDateTime.getHour(), untilLocalDateTime.getMinute(), 59);
                break;
            case SECOND:
                this.fromLocalDateTime = fromLocalDateTime;
                this.untilLocalDateTime = untilLocalDateTime;
                break;
        }
    }
}
