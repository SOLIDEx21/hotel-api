package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.PropertyRequest;
import com.example.hotelapi.dto.PropertyResponse;
import com.example.hotelapi.dto.UserResponse;
import com.example.hotelapi.entity.Property;
import com.example.hotelapi.entity.User;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.repository.PropertyRepository;
import com.example.hotelapi.repository.UserRepository;
import com.example.hotelapi.service.PropertyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyServiceImpl(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PropertyResponse createProperty(PropertyRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + request.getOwnerId()));

        Property property = new Property();
        property.setName(request.getName());
        property.setPropertyType(request.getPropertyType());
        property.setAddress(request.getAddress());
        property.setCity(request.getCity());
        property.setDescription(request.getDescription());
        property.setOwner(owner);

        Property savedProperty = propertyRepository.save(property);
        return mapToDto(savedProperty);
    }

    @Override
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        return mapToDto(property);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + id));
        propertyRepository.delete(property);
    }

    private PropertyResponse mapToDto(Property property) {
        PropertyResponse response = new PropertyResponse();
        response.setId(property.getId());
        response.setName(property.getName());
        response.setPropertyType(property.getPropertyType());
        response.setAddress(property.getAddress());
        response.setCity(property.getCity());
        response.setDescription(property.getDescription());

        if (property.getOwner() != null) {
            UserResponse ownerDto = new UserResponse();
            ownerDto.setId(property.getOwner().getId());
            ownerDto.setFullName(property.getOwner().getFullName());
            ownerDto.setEmail(property.getOwner().getEmail());
            ownerDto.setRole(property.getOwner().getRole());
            response.setOwner(ownerDto);
        }

        return response;
    }
}
