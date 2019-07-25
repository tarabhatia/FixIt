package com.example.fixit;

public class Location {
    private Double latitude;
    private Double longitude;
    private String address;
    private String name;

    public Location(){}

    public Location(Double lat, Double lng, String adr, String name){
        this.latitude = lat;
        this.longitude = lng;
        this.address = adr;
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
