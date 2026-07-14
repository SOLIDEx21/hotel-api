package com.example.hotelapi.service.impl;

import com.example.hotelapi.dto.UserRequest;
import com.example.hotelapi.dto.UserResponse;
import com.example.hotelapi.entity.User;
import com.example.hotelapi.exception.ResourceNotFoundException;
import com.example.hotelapi.repository.UserRepository;
import com.example.hotelapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse register(UserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // mock hashing
        user.setRole(request.getRole() != null ? request.getRole() : "GUEST");

        User savedUser = userRepository.save(user);
        return mapToDto(savedUser);
    }

    @Override
    public UserResponse login(String email, String password) {
        // Mock login
        return null; // Will implement properly later or assume it's just a placeholder
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapToDto(user);
    }

    private UserResponse mapToDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
