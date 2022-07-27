package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    boolean existsByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<Store> findByNameAndPhoneNumber(String name, String phoneNumber);

    @EntityGraph(attributePaths = {"storeImage"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Store> findAllBy(Pageable pageable);
}
