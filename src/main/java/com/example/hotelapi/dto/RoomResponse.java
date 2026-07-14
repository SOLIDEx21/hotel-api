package com.example.hotelapi.dto;

import java.math.BigDecimal;

public class RoomResponse {
    private Long id;
    private PropertyResponse property;
    private String roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PropertyResponse getProperty() { return property; }
    public void setProperty(PropertyResponse property) { this.property = property; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }
}
