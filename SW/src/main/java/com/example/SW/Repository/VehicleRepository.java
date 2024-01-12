package com.example.SW.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SW.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
