package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    boolean existsByStudentId(Long studentId);

    boolean existsByName(String name);

    Optional<User> findByStudentId(Long studentId);

    boolean existsUniqueNumber(String uniqueNumber);

    Optional<T> findByUniqueNumber(String uniqueNumber);

}
