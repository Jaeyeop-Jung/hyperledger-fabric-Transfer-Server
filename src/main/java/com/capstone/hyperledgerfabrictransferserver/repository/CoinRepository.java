package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findByName(String coinName);

    boolean existsByName(String coinName);
}
