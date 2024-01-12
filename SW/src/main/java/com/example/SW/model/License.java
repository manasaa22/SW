package com.example.SW.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String licenseNumber;
    private String licenseState;
    private String licenseExpirationDate;

    public License() {

    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Driver driver;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseState() {
        return licenseState;
    }

    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }

    public String getLicenseExpirationDate() {
        return licenseExpirationDate;
    }

    public void setLicenseExpirationDate(String licenseExpirationDate) {
        this.licenseExpirationDate = licenseExpirationDate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

}
