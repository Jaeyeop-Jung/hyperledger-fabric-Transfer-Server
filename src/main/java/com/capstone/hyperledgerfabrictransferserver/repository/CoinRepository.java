package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    boolean existsByName(String coinName);

    Optional<Coin> findByName(String coinName);
}
