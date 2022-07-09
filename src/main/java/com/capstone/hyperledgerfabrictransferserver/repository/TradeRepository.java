package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    Page<Trade> findAllBySenderOrReceiver(User sender, User receiver, Pageable pageable);

    @Query(value = "select t from Trade as t " +
            "where t.dateCreated between :fromDateCreated and :untilDateCreated " +
            "and (t.sender.studentId = :senderStudentId or :senderStudentId is null )" +
            "and (t.receiver.studentId = :receiverStudentId or :receiverStudentId is null )")
     Page<Trade> findAllBySenderStudentIdAndReceiverStudentIdIfNullThenAllAndBetween(
             LocalDateTime fromDateCreated,
             LocalDateTime untilDateCreated,
             Long senderStudentId,
             Long receiverStudentId,
             Pageable pageable
    );

}
