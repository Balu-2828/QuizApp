package com.project.QuizApp.dao;

import com.project.QuizApp.model.UserAndAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAndAdmin,Long> {
    Optional<UserAndAdmin> findByUsername(String username);
}
