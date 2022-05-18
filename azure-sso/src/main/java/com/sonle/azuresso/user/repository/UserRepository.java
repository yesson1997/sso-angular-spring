package com.sonle.azuresso.security.repository;

import com.sonle.azuresso.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User findByUsername(String username);
    @Transactional
    void deleteByUsername(String username);
}
