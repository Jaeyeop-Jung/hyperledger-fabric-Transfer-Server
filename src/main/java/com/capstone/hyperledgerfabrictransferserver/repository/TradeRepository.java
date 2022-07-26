package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    Page<Trade> findAllBySenderOrReceiver(User sender, User receiver, Pageable pageable);
    Page<Trade> findAllBySenderOrReceiverAndDateCreatedBetween(
            User sender,
            User receiver,
            LocalDateTime fromDateCreated,
            LocalDateTime untilDateCreated,
            Pageable pageable
    );

    Optional<Trade> findByTransactionId(String transactionId);

}
