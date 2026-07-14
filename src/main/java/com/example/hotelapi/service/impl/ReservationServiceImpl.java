package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.ReservationRequest;
import com.example.hotelapi.dto.ReservationResponse;
import com.example.hotelapi.dto.RoomResponse;
import com.example.hotelapi.dto.UserResponse;
import com.example.hotelapi.entity.Reservation;
import com.example.hotelapi.entity.Room;
import com.example.hotelapi.entity.User;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.exception.RoomAlreadyBookedException;
import com.example.hotelapi.repository.ReservationRepository;
import com.example.hotelapi.repository.RoomRepository;
import com.example.hotelapi.repository.UserRepository;
import com.example.hotelapi.service.ReservationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        // Check for conflicts
        boolean isConflict = reservationRepository.existsConflictingReservation(
                room.getId(), request.getCheckInDate(), request.getCheckOutDate());

        if (isConflict) {
            throw new RoomAlreadyBookedException("Room is already booked for the selected dates");
        }

        long days = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        if (days <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(days));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus("CONFIRMED");

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToDto(savedReservation);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return mapToDto(reservation);
    }

    @Override
    public List<ReservationResponse> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReservationResponse mapToDto(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setTotalPrice(reservation.getTotalPrice());
        response.setStatus(reservation.getStatus());

        if (reservation.getUser() != null) {
            UserResponse userDto = new UserResponse();
            userDto.setId(reservation.getUser().getId());
            userDto.setFullName(reservation.getUser().getFullName());
            userDto.setEmail(reservation.getUser().getEmail());
            userDto.setRole(reservation.getUser().getRole());
            response.setUser(userDto);
        }

        if (reservation.getRoom() != null) {
            RoomResponse roomDto = new RoomResponse();
            roomDto.setId(reservation.getRoom().getId());
            roomDto.setRoomType(reservation.getRoom().getRoomType());
            roomDto.setCapacity(reservation.getRoom().getCapacity());
            roomDto.setPricePerNight(reservation.getRoom().getPricePerNight());
            response.setRoom(roomDto);
        }

        return response;
    }
}
