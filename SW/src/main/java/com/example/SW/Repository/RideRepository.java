package com.example.SW.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SW.model.Ride;

public interface RideRepository extends JpaRepository<Ride, Integer> {

}
