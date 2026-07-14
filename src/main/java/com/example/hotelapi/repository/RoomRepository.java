package com.example.hotelapi.repository;

import com.example.hotelapi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Müəyyən bir otelə aid olan bütün otaqları tapmaq üçün
    List<Room> findByPropertyId(Long propertyId);
}