package com.customer.customerclient.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name="Customers")
@Entity
public class Customer {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(name = "Name")
    private String customerName;
    @Column(name = "Email")
    private String customerEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private CustomerAddress customerBillingAddress;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress customerShippingAddress;
    
    public Customer() {
    }

    public Customer(Long customerId, String customerName, String customerEmail, CustomerAddress customerBillingAddress,
            CustomerAddress customerShippingAddress) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerBillingAddress = customerBillingAddress;
        this.customerShippingAddress = customerShippingAddress;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerEmail="
                + customerEmail + ", customerBillingAddress=" + customerBillingAddress + ", customerShippingAddress="
                + customerShippingAddress + "]";
    }

    
}


    