package com.example.SW.service;

import com.example.SW.exceptions.DriverException;
import com.example.SW.exceptions.RideException;
import com.example.SW.model.Driver;
import com.example.SW.model.Ride;
import com.example.SW.model.User;
import com.example.SW.request.RideRequest;

public interface RideService {
    public Ride requestRide(RideRequest rideRequest, User user) throws DriverException;

    public Ride createRideRequest(User user, Driver nearesDriver,
            double picupLatitude, double pickupLongitude,
            double destinationLatitude, double destinationLongitude,
            String pickupArea, String destinationArea);

    public void acceptRide(Integer rideId) throws RideException;

    public void declineRide(Integer rideId, Integer driverId) throws RideException;

    public void startRide(Integer rideId, int opt) throws RideException;

    public void completeRide(Integer rideId) throws RideException;

    public void cancleRide(Integer rideId) throws RideException;

    public Ride findRideById(Integer rideId) throws RideException;
}
