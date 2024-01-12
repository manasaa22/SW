package com.example.SW.Controller.mapper;

import org.springframework.stereotype.Service;
import com.example.SW.Dtos.DriverDTO;
import com.example.SW.Dtos.RideDTO;
import com.example.SW.Dtos.UserDTO;
import com.example.SW.model.Driver;
import com.example.SW.model.Ride;
import com.example.SW.model.User;

@Service
public class DtoMapper {
    public static DriverDTO toDriverDto(Driver driver) {

        DriverDTO driverDto = new DriverDTO();

        driverDto.setEmail(driver.getEmail());
        driverDto.setId(driver.getId());
        driverDto.setLatitude(driver.getLalitude());
        driverDto.setLongitude(driver.getLongitutde());
        driverDto.setMobile(driver.getMobile());
        driverDto.setName(driver.getName());
        driverDto.setRole(driver.getRole());
        driverDto.setVehicle(driver.getVehicle());

        return driverDto;

    }

    public static UserDTO toUserDto(User user) {

        UserDTO userDto = new UserDTO();

        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setMobile(user.getMobile());
        userDto.setName(user.getFullname());

        return userDto;

    }

    public static RideDTO toRideDto(Ride ride) {
        DriverDTO driverDTO = toDriverDto(ride.getDriver());
        UserDTO userDto = toUserDto(ride.getUser());

        RideDTO rideDto = new RideDTO();

        rideDto.setDestinationLatitude(ride.getDestinationLalitude());
        rideDto.setDestinationLongitude(ride.getDestinationLongitude());
        rideDto.setDistance(ride.getDistance());
        rideDto.setDriver(driverDTO);
        rideDto.setDuration(ride.getDuration());
        rideDto.setEndTime(ride.getEndTime());

        rideDto.setId(ride.getId());
        rideDto.setPickupLatitude(ride.getPickupLalitude());
        rideDto.setPickupLongitude(ride.getPickupLongitude());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setStatus(ride.getStatus());
        rideDto.setUser(userDto);
        rideDto.setPickupArea(ride.getPickupArea());
        rideDto.setDestinationArea(ride.getDestinationArea());
        rideDto.setOtp(ride.getOtp());
        return rideDto;
    }
}
