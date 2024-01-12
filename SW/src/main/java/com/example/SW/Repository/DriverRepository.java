package com.example.SW.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.SW.model.Driver;
import com.example.SW.model.Ride;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    public Driver findByEmail(String email);

    @Query("SELECT R FROM Ride R WHERE R.status=REQUESTED AND R.driver.id=:driverId")
    public List<Ride> getAllocatedRides(@Param("driverId") Integer driverId);

    @Query("SELECT R FROM Ride R where R.status=COMPLETED AND R.driver.Id=:driverId")
    public List<Ride> getCompletedRides(@Param("driverId") Integer driverId);
}
