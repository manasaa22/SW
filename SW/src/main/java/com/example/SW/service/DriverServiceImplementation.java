package com.example.SW.service;

import com.example.SW.Repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SW.Repository.DriverRepository;
import com.example.SW.Repository.LicenseRepository;
import com.example.SW.Repository.RideRepository;
import com.example.SW.config.JwtUti;
import com.example.SW.domain.RideStatus;
import com.example.SW.domain.UserRole;
import com.example.SW.exceptions.DriverException;
import com.example.SW.model.Driver;
import com.example.SW.model.License;
import com.example.SW.model.Ride;
import com.example.SW.model.Vehicle;
import com.example.SW.request.DriverSignupRequest;

@Service
public class DriverServiceImplementation implements DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private Calculaters distanceCalculator;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUti jwtUtil;
    @Autowired
    private VehicleRepository VehicleRepository;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private RideRepository rideRepository;

    @Override
    public List<Driver> getAvailableDrivers(double pickupLatitude, double picupLongitude, double radius, Ride ride) {
        List<Driver> allDrivers = driverRepository.findAll();

        List<Driver> availableDriver = new ArrayList<>();

        for (Driver driver : allDrivers) {

            if (driver.getCurrentRide() != null && driver.getCurrentRide().getStatus() != RideStatus.COMPLETED) {

                continue;
            }
            if (ride.getDeclinedDrivers().contains(driver.getId())) {
                System.out.println("its containes");
                continue;
            }

            double driverLatitude = driver.getLalitude();
            double driverLongitude = driver.getLongitutde();

            double distance = distanceCalculator.calculateDistance(driverLatitude, driverLongitude, pickupLatitude,
                    picupLongitude);

            availableDriver.add(driver);
        }

        return availableDriver;
    }

    @Override
    public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {
        double min = Double.MAX_VALUE;
        ;
        Driver nearestDriver = null;

        // List<Driver> drivers=new ArrayList<>();
        // double minAuto

        for (Driver driver : availableDrivers) {
            double driverLatitude = driver.getLalitude();
            double driverLongitude = driver.getLongitutde();

            double distance = distanceCalculator.calculateDistance(pickupLatitude, pickupLongitude, driverLatitude,
                    driverLongitude);

            if (min > distance) {
                min = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }

    @Override
    public Driver getReqDriverProfile(String jwt) throws DriverException {
        String email = jwtUtil.getEmailFromJwt(jwt);
        Driver driver = driverRepository.findByEmail(email);
        if (driver == null) {
            throw new DriverException("driver not exist with email " + email);
        }

        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Integer driverId) throws DriverException {
        Driver driver = findDriverById(driverId);
        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRides(Integer driveId) throws DriverException {
        List<Ride> allocatedRides = driverRepository.getAllocatedRides(driveId);
        return allocatedRides;
    }

    @Override
    public Driver findDriverById(Integer driverId) throws DriverException {
        Optional<Driver> opt = driverRepository.findById(driverId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new DriverException("does not exist with id" + driverId);
    }

    @Override
    public List<Ride> completedRids(Integer driverId) throws DriverException {
        List<Ride> rides = driverRepository.getCompletedRides(driverId);
        return rides;
    }

    @Override
    public Driver registerDriver(DriverSignupRequest driverSignupRequest) {
        License license = driverSignupRequest.getLicense();
        Vehicle vehicle = driverSignupRequest.getVehicle();

        License createdLicense = new License();

        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseExpirationDate(license.getLicenseExpirationDate());
        createdLicense.setId(license.getId());

        License savedLicense = licenseRepository.save(createdLicense);

        Vehicle createdVehicle = new Vehicle();

        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setId(vehicle.getId());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModel(vehicle.getModel());
        createdVehicle.setYear(vehicle.getYear());

        Vehicle savedVehicle = VehicleRepository.save(createdVehicle);

        Driver driver = new Driver();

        String encodedPassword = passwordEncoder.encode(driverSignupRequest.getPassword());

        driver.setEmail(driverSignupRequest.getEmail());
        driver.setName(driverSignupRequest.getName());
        driver.setMobile(driverSignupRequest.getMobile());
        driver.setPassword(encodedPassword);
        driver.setLicense(savedLicense);
        driver.setVehicle(savedVehicle);
        driver.setRole(UserRole.DRIVER);

        driver.setLalitude(driverSignupRequest.getLalitude());
        driver.setLongitutde(driverSignupRequest.getLongitude());

        Driver createdDriver = driverRepository.save(driver);

        savedLicense.setDriver(createdDriver);
        savedVehicle.setDriver(createdDriver);

        licenseRepository.save(savedLicense);
        VehicleRepository.save(savedVehicle);

        return createdDriver;

    }

}
