package com.capstone.hyperledgerfabrictransferserver.repository;

import com.capstone.hyperledgerfabrictransferserver.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface StudentRepository extends UserRepository<Student> {

    Optional<Student> findByStudentNumber(String studentNumber);

    boolean existsByStudentNumber(String uniqueNumber);

    @Override
    default boolean existsUniqueNumber(String uniqueNumber) {
        return existsByStudentNumber(uniqueNumber);
    }

    @Override
    Optional<Student> findByUniqueNumber(String uniqueNumber);
}
