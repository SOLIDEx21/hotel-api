package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Müəyyən bir müştərinin rezervasiyalarını tapmaq üçün
    List<Reservation> findByUserId(Long userId);

    @org.springframework.data.jpa.repository.Query("SELECT count(r) > 0 FROM Reservation r WHERE r.room.id = :roomId AND r.status != 'CANCELLED' AND r.checkInDate < :checkOutDate AND r.checkOutDate > :checkInDate")
    boolean existsConflictingReservation(
            @org.springframework.data.repository.query.Param("roomId") Long roomId, 
            @org.springframework.data.repository.query.Param("checkInDate") java.time.LocalDate checkInDate, 
            @org.springframework.data.repository.query.Param("checkOutDate") java.time.LocalDate checkOutDate);
}