package com.example.hotelapi.service;

import com.example.hotelapi.dto.ReservationRequest;
import com.example.hotelapi.dto.ReservationResponse;
import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getReservationsByUserId(Long userId);
}
