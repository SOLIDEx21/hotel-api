package com.example.hotelapi.controller;

import com.example.hotelapi.entity.Hotel;
import com.example.hotelapi.repository.HotelRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Bütün otelləri görmək üçün GET sorğusu
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Sistemə yeni otel əlavə etmək üçün POST sorğusu
    @PostMapping
    public Hotel addHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}