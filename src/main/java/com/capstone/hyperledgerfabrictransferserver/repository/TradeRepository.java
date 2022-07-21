package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.Trade;
import com.capstone.hyperledgerfabrictransferserver.dto.TransferResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "select " +
                        "t.transactionId as transactionId," +
                        "case when u1.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u1.id) " +
                            "when u1.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u1.id) " +
                            "else (select a.id from Admin a where a.id = u1.id) " +
                        "end as senderUniqueNumber, " +
                        "u1.name as senderName, " +
                        "case when u2.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u2.id) " +
                            "when u2.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u2.id) " +
                            "else (select a.id from Admin a where a.id = u2.id) " +
                        "end as receiverUniqueNumber, " +
                        "u2.name as receiverName, " +
                        "t.amount as amount," +
                        "t.dateCreated as dateCreated " +
                   "from Trade t " +
                   "inner join User u1 on t.sender " +
                   "inner join User u2 on t.receiver " +
                   "where t.sender = :sender or t.receiver = :receiver"
    )
    Page<TransferResponse> findAllBySenderOrReceiver(User sender, User receiver, Pageable pageable);

    @Query(value = "select " +
                        "t.transactionId as transactionId," +
                        "case when u1.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u1.id) " +
                             "when u1.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u1.id) " +
                             "else (select a.id from Admin a where a.id = u1.id) " +
                        "end as senderUniqueNumber, " +
                        "u1.name as senderName, " +
                        "case when u2.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u2.id) " +
                            "when u2.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u2.id) " +
                            "else (select a.id from Admin a where a.id = u2.id) " +
                        "end as receiverUniqueNumber, " +
                        "u2.name as receiverName, " +
                        "t.amount as amount," +
                        "t.dateCreated as dateCreated " +
                   "from Trade t " +
                   "inner join User u1 on t.sender " +
                   "inner join User u2 on t.receiver " +
                   "where (t.sender = :sender or :sender is null) " +
                   "and (t.receiver = :receiver or :receiver is null)"
    )
    Page<TransferResponse> findAllBySenderOrReceiverBetween(
            LocalDateTime fromDateCreated,
            LocalDateTime untilDateCreated,
            User sender,
            User receiver,
            Pageable pageable
    );

    @Query(value = "select " +
                        "t.transactionId as transactionId," +
                        "case when u1.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u1.id) " +
                             "when u1.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u1.id) " +
                             "else (select a.id from Admin a where a.id = u1.id) " +
                        "end as senderUniqueNumber, " +
                        "u1.name as senderName, " +
                        "case when u2.userRole = 'ROLE_USER' then (select s.studentNumber from Student s where s.id = u2.id) " +
                            "when u2.userRole = 'ROLE_STOREMANAGER' then (select s.phoneNumber from StoreManager s where s.id = u2.id) " +
                            "else (select a.id from Admin a where a.id = u2.id) " +
                        "end as receiverUniqueNumber, " +
                        "u2.name as receiverName, " +
                        "t.amount as amount," +
                        "t.dateCreated as dateCreated " +
                    "from Trade t " +
                    "inner join User u1 on t.sender " +
                    "inner join User u2 on t.receiver " +
                    "where t.id = :id"
    )
    TransferResponse findTradeById(Long id);


    Optional<Trade> findByTransactionId(String transactionId);

}
