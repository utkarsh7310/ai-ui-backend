package com.wowfood.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom functions login check karne ke liye
    User findByEmailAndPassword(String email, String password);
    User findByPhoneAndPassword(String phone, String password);
    boolean existsByPhone(String phone);
}