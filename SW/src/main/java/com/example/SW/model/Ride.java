package com.example.SW.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.SW.domain.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @JsonIgnore
    private List<Integer> declinedDrivers = new ArrayList<>();

    private double pickupLalitude;
    private double pickupLongitude;
    private double destinationLalitude;
    private double destinationLongitude;
    private String pickupArea;
    private String destinationArea;
    private double distance;
    private long duration;
    private RideStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int otp;

    public Ride() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public List<Integer> getDeclinedDrivers() {
        return declinedDrivers;
    }

    public void setDeclinedDrivers(List<Integer> declinedDrivers) {
        this.declinedDrivers = declinedDrivers;
    }

    public double getPickupLalitude() {
        return pickupLalitude;
    }

    public void setPickupLalitude(double pickupLalitude) {
        this.pickupLalitude = pickupLalitude;
    }

    public double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public double getDestinationLalitude() {
        return destinationLalitude;
    }

    public void setDestinationLalitude(double destinationLalitude) {
        this.destinationLalitude = destinationLalitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getPickupArea() {
        return pickupArea;
    }

    public void setPickupArea(String pickupArea) {
        this.pickupArea = pickupArea;
    }

    public String getDestinationArea() {
        return destinationArea;
    }

    public void setDestinationArea(String destinationArea) {
        this.destinationArea = destinationArea;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

}
