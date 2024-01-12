package com.example.SW.service;

import java.util.List;

import com.example.SW.exceptions.DriverException;
import com.example.SW.model.Driver;
import com.example.SW.model.Ride;
import com.example.SW.request.DriverSignupRequest;

public interface DriverService {

    public Driver registerDriver(DriverSignupRequest driverSignupRequest);

    public List<Driver> getAvailableDrivers(double pickupLatitude,
            double picupLongitude, double radius, Ride ride);

    public Driver findNearestDriver(List<Driver> availableDrivers,
            double picupLatitude, double picupLongitude);

    public Driver getReqDriverProfile(String jwt) throws DriverException;

    public Ride getDriversCurrentRide(Integer driverId) throws DriverException;

    public List<Ride> getAllocatedRides(Integer driverId) throws DriverException;

    public Driver findDriverById(Integer driverId) throws DriverException;

    public List<Ride> completedRids(Integer driverId) throws DriverException;
}
