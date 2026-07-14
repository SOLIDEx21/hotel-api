package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Müəyyən bir müştərinin rezervasiyalarını tapmaq üçün
    List<Reservation> findByUserId(Long userId);
}