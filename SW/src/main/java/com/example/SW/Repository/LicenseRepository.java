package com.example.SW.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SW.model.License;

public interface LicenseRepository extends JpaRepository<License, Integer> {

}