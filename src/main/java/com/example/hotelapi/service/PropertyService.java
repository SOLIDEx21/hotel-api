package com.example.hotelapi.service;

import com.example.hotelapi.dto.PropertyRequest;
import com.example.hotelapi.dto.PropertyResponse;
import java.util.List;

public interface PropertyService {
    PropertyResponse createProperty(PropertyRequest request);
    PropertyResponse getPropertyById(Long id);
    List<PropertyResponse> getAllProperties();
    void deleteProperty(Long id);
}
