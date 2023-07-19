package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerAddress {

    @JsonProperty("doorNo")
    private Long doorNo;
    @JsonProperty("streetName")
    private String streetName;
    @JsonProperty("layout")
    private String layout;
    @JsonProperty("city")
    private String city;
    @JsonProperty("pincode")
    private Long pincode;
    
    public CustomerAddress() {
    }

    public CustomerAddress(Long doorNo, String streetName, String layout, String city, Long pincode) {
        this.doorNo = doorNo;
        this.streetName = streetName;
        this.layout = layout;
        this.city = city;
        this.pincode = pincode;
    }

    public Long getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(Long doorNo) {
        this.doorNo = doorNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "CustomerAddress [doorNo=" + doorNo + ", streetName=" + streetName + ", layout=" + layout + ", city="
                + city + ", pincode=" + pincode + "]";
    }
    
}
