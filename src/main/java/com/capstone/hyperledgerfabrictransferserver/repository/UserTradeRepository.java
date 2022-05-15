package com.capstone.hyperledgerfabrictransferserver.repository;


import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.cloudant.client.api.query.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {

//    List<UserTrade> findAllBySenderAnd(User sender, );

}
