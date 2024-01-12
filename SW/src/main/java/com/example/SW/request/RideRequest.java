package com.example.SW.request;

public class RideRequest {
    private double pickupLongitude;
    private double pickupLatitude;
    private double destinationLongitude;
    private double destinationLatitude;
    private String pickupArea;
    private String destinatioArea;

    public RideRequest() {

    }

    public double getPickupLongitude() {
        return pickupLongitude;
    }

    public void setPickupLongitude(double pickupLongitude) {
        this.pickupLongitude = pickupLongitude;
    }

    public double getPickupLatitude() {
        return pickupLatitude;
    }

    public void setPickupLatitude(double pickupLatitude) {
        this.pickupLatitude = pickupLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public String getPickupArea() {
        return pickupArea;
    }

    public void setPickupArea(String pickupArea) {
        this.pickupArea = pickupArea;
    }

    public String getDestinatioArea() {
        return destinatioArea;
    }

    public void setDestinatioArea(String destinatioArea) {
        this.destinatioArea = destinatioArea;
    }

}
