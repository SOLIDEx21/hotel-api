package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.PropertyResponse;
import com.example.hotelapi.dto.RoomRequest;
import com.example.hotelapi.dto.RoomResponse;
import com.example.hotelapi.dto.UserResponse;
import com.example.hotelapi.entity.Property;
import com.example.hotelapi.entity.Room;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.repository.PropertyRepository;
import com.example.hotelapi.repository.RoomRepository;
import com.example.hotelapi.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;

    public RoomServiceImpl(RoomRepository roomRepository, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));

        Room room = new Room();
        room.setProperty(property);
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());

        Room savedRoom = roomRepository.save(room);
        return mapToDto(savedRoom);
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        return mapToDto(room);
    }

    @Override
    public List<RoomResponse> getRoomsByPropertyId(Long propertyId) {
        return roomRepository.findByPropertyId(propertyId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        roomRepository.delete(room);
    }

    private RoomResponse mapToDto(Room room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setRoomType(room.getRoomType());
        response.setCapacity(room.getCapacity());
        response.setPricePerNight(room.getPricePerNight());

        if (room.getProperty() != null) {
            PropertyResponse propDto = new PropertyResponse();
            propDto.setId(room.getProperty().getId());
            propDto.setName(room.getProperty().getName());
            propDto.setPropertyType(room.getProperty().getPropertyType());
            propDto.setAddress(room.getProperty().getAddress());
            propDto.setCity(room.getProperty().getCity());
            propDto.setDescription(room.getProperty().getDescription());
            
            if (room.getProperty().getOwner() != null) {
                UserResponse ownerDto = new UserResponse();
                ownerDto.setId(room.getProperty().getOwner().getId());
                ownerDto.setFullName(room.getProperty().getOwner().getFullName());
                ownerDto.setEmail(room.getProperty().getOwner().getEmail());
                ownerDto.setRole(room.getProperty().getOwner().getRole());
                propDto.setOwner(ownerDto);
            }
            response.setProperty(propDto);
        }

        return response;
    }
}
