package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    @JsonProperty("customerName")
    private String customerName;
    @JsonProperty("customerEmail")
    private String customerEmail;
    @JsonProperty("customerBillingAddress")
    private CustomerAddress customerBillingAddress;
    @JsonProperty("customerShippingAddress")
    private CustomerAddress customerShippingAddress;
    
    public Customer() {
    }

    public Customer(String customerName, String customerEmail, CustomerAddress customerBillingAddress,
            CustomerAddress customerShippingAddress) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerBillingAddress = customerBillingAddress;
        this.customerShippingAddress = customerShippingAddress;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public CustomerAddress getCustomerBillingAddress() {
        return customerBillingAddress;
    }

    public void setCustomerBillingAddress(CustomerAddress customerBillingAddress) {
        this.customerBillingAddress = customerBillingAddress;
    }

    public CustomerAddress getCustomerShippingAddress() {
        return customerShippingAddress;
    }

    public void setCustomerShippingAddress(CustomerAddress customerShippingAddress) {
        this.customerShippingAddress = customerShippingAddress;
    }

}


    