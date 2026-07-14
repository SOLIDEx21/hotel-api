package com.example.hotelapi.repository;

import com.example.hotelapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // E-poçt ünvanına görə istifadəçini tapmaq üçün hazır metod
    User findByEmail(String email);
}