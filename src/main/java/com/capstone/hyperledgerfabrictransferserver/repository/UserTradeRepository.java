package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {

    List<UserTrade> findAllById(Long userId, Sort dateCreated);
}
