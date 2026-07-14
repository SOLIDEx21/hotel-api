package com.example.hotelapi.service;

import com.example.hotelapi.dto.RoomRequest;
import com.example.hotelapi.dto.RoomResponse;
import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomRequest request);
    RoomResponse getRoomById(Long id);
    List<RoomResponse> getRoomsByPropertyId(Long propertyId);
    void deleteRoom(Long id);
}
