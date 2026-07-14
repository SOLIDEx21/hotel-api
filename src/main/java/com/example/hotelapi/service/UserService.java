package com.example.hotelapi.service;

import com.example.hotelapi.dto.UserRequest;
import com.example.hotelapi.dto.UserResponse;

public interface UserService {
    UserResponse register(UserRequest request);
    UserResponse login(String email, String password);
    UserResponse getUserById(Long id);
}
