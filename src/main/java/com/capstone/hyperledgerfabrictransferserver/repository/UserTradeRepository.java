package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {
}
