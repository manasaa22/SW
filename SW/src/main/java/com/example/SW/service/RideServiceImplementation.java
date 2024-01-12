package com.example.SW.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.management.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SW.Repository.DriverRepository;
import com.example.SW.Repository.RideRepository;
import com.example.SW.domain.RideStatus;
import com.example.SW.exceptions.DriverException;
import com.example.SW.exceptions.RideException;
import com.example.SW.model.Driver;
import com.example.SW.model.Ride;
import com.example.SW.model.User;
import com.example.SW.request.RideRequest;

@Service
public class RideServiceImplementation implements RideService {
    @Autowired
    private DriverService driverService;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private Calculaters calculaters;
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Ride requestRide(RideRequest rideRequest, User user) throws DriverException {

        double picupLatitude = rideRequest.getPickupLatitude();
        double picupLongitude = rideRequest.getPickupLongitude();
        double destinationLatitude = rideRequest.getDestinationLatitude();
        double destinationLongitude = rideRequest.getDestinationLongitude();
        String pickupArea = rideRequest.getPickupArea();
        String destinationArea = rideRequest.getDestinatioArea();

        Ride existingRide = new Ride();

        List<Driver> availableDrivers = driverService.getAvailableDrivers(picupLatitude,
                picupLongitude, 5, existingRide);

        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, picupLatitude, picupLongitude);

        if (nearestDriver == null) {
            throw new DriverException("Driver not available");
        }

        System.out.println(" duration ----- before ride ");

        Ride ride = createRideRequest(user, nearestDriver,
                picupLatitude, picupLongitude,
                destinationLatitude, destinationLongitude,
                pickupArea, destinationArea);

        System.out.println(" duration ----- after ride ");
        return ride;
    }

    @Override
    public Ride createRideRequest(User user, Driver nearesDriver, double pickupLatitude, double pickupLongitude,
            double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea) {
        Ride ride = new Ride();

        ride.setDriver(nearesDriver);
        ride.setUser(user);
        ride.setPickupLalitude(pickupLatitude);
        ride.setPickupLongitude(pickupLongitude);
        ride.setDestinationLalitude(destinationLatitude);
        ride.setDestinationLongitude(destinationLongitude);
        ride.setStatus(RideStatus.REQUESTED);
        ride.setPickupArea(pickupArea);
        ride.setDestinationArea(destinationArea);

        System.out.println(" ----- a - " + pickupLatitude);

        return rideRepository.save(ride);
    }

    @Override
    public void acceptRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);

        ride.setStatus(RideStatus.ACCEPTED);

        Driver driver = ride.getDriver();

        driver.setCurrentRide(ride);

        Random random = new Random();

        int otp = random.nextInt(9000) + 1000;
        ride.setOtp(otp);

        driverRepository.save(driver);

        rideRepository.save(ride);
    }

    @Override
    public void declineRide(Integer rideId, Integer driverId) throws RideException {
        Ride ride = findRideById(rideId);
        System.out.println(ride.getId());

        ride.getDeclinedDrivers().add(driverId);

        System.out.println(ride.getId() + " - " + ride.getDeclinedDrivers());

        List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLalitude(),
                ride.getPickupLongitude(), 5, ride);

        Driver nearestDriver = driverService.findNearestDriver(availableDrivers, ride.getPickupLalitude(),
                ride.getPickupLongitude());

        ride.setDriver(nearestDriver);

        rideRepository.save(ride);
    }

    @Override
    public void startRide(Integer rideId, int otp) throws RideException {
        Ride ride = findRideById(rideId);

        if (otp != ride.getOtp()) {
            throw new RideException("please provide a valid otp");
        }

        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);
    }

    @Override
    public void completeRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);

        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());
        ;

        double distence = calculaters.calculateDistance(ride.getDestinationLalitude(), ride.getDestinationLongitude(),
                ride.getPickupLalitude(), ride.getPickupLongitude());

        LocalDateTime start = ride.getStartTime();
        LocalDateTime end = ride.getEndTime();
        Duration duration = Duration.between(start, end);
        long milliSecond = duration.toMillis();

        System.out.println("duration ------- " + milliSecond);

        ride.setDistance(Math.round(distence * 100.0) / 100.0);
        ride.setDuration(milliSecond);
        ride.setEndTime(LocalDateTime.now());

        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);

        driverRepository.save(driver);
        rideRepository.save(ride);
    }

    @Override
    public void cancleRide(Integer rideId) throws RideException {
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }

    @Override
    public Ride findRideById(Integer rideId) throws RideException {
        Optional<Ride> ride = rideRepository.findById(rideId);
        if (ride.isPresent()) {
            return ride.get();
        }
        throw new RideException("Ride not exist with id" + rideId);
    }

}
