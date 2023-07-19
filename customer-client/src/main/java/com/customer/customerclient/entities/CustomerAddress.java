package com.customer.customerclient.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Addresses")
@Entity
public class CustomerAddress {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId; 
    private Long doorNo;
    private String streetName;
    private String layout;
    private String city;
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
