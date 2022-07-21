package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import com.capstone.hyperledgerfabrictransferserver.domain.StoreManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreManagerRepository extends UserRepository<StoreManager> {

    Optional<StoreManager> findByPhoneNumber(String uniqueNumber);

    boolean existsByPhoneNumber(String uniqueNumber);

    @Override
    default boolean existsUniqueNumber(String uniqueNumber) {
        return existsByPhoneNumber(uniqueNumber);
    }

    @Override
    default Optional<StoreManager> findByUniqueNumber(String uniqueNumber) {
        return findByPhoneNumber(uniqueNumber);
    }
}
