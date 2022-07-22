package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserRole;
import com.capstone.hyperledgerfabrictransferserver.dto.UserJoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByIdentifier(String identifier);

    boolean existsByName(String name);

    Optional<User> findByIdentifier(String identifier);;

}
