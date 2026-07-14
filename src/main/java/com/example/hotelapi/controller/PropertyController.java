package com.example.hotelapi.controller;

import com.example.hotelapi.entity.Property;
import com.example.hotelapi.repository.PropertyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class PropertyController {

    private final PropertyRepository hotelRepository;

    public PropertyController(PropertyRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    // Bütün otelləri görmək üçün GET sorğusu
    @GetMapping
    public List<Property> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Sistemə yeni otel əlavə etmək üçün POST sorğusu
    @PostMapping
    public Property addHotel(@RequestBody Property hotel) {
        return hotelRepository.save(hotel);
    }
}