package com.customer.customerclient.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.customerclient.entities.Customer;
import com.customer.customerclient.entities.CustomerAddress;
import com.customer.customerclient.repositories.AddressRepository;
import com.customer.customerclient.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    // Save the new customer to the repository
    public String addCustomer(Customer customer) {
        customerRepository.save(customer);
        return "Customer added successfully.";
    }

    // Save new address to the repository
    public String createAddress(CustomerAddress customerAddress) {
        addressRepository.save(customerAddress);
        return "Address created successfully.";
    }

    // Get all customers
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
    
    // Get customer details by email
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByCustomerEmail(email).orElse(null);
    }

    // Get customer Id by email
    public Long getCustomerIdByEmail(String email) {
        Customer customer = customerRepository.findByCustomerEmail(email).orElse(null);
        if (customer != null) {
            return customer.getCustomerId();
        }
        return null;
    }
    
    // Retrieve the customer details by customer ID from the repository
    public Customer getCustomerDetails(Long customerId) {
        return customerRepository.findByCustomerId(customerId).orElse(null);
    }

    // Update the customer details, Update the customer details and Update the shipping address
    public void editCustomerDetails(Long customerId, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findByCustomerId(customerId).orElse(null);

        if (existingCustomer != null) {
            // 
            existingCustomer.setCustomerName(updatedCustomer.getCustomerName());
            existingCustomer.setCustomerEmail(updatedCustomer.getCustomerEmail());

            CustomerAddress updatedBillingAddress = updatedCustomer.getCustomerBillingAddress();
            CustomerAddress existingBillingAddress = existingCustomer.getCustomerBillingAddress();
            if (updatedBillingAddress != null) {
                updateAddress(existingBillingAddress, updatedBillingAddress);
            }

            CustomerAddress updatedShippingAddress = updatedCustomer.getCustomerShippingAddress();
            CustomerAddress existingShippingAddress = existingCustomer.getCustomerShippingAddress();
            if (updatedShippingAddress != null) {
                updateAddress(existingShippingAddress, updatedShippingAddress);
            }

            customerRepository.save(existingCustomer);
        }
    }

    // helper method for the update customer method
    private void updateAddress(CustomerAddress existingAddress, CustomerAddress updatedAddress) {
        existingAddress.setDoorNo(updatedAddress.getDoorNo());
        existingAddress.setStreetName(updatedAddress.getStreetName());
        existingAddress.setLayout(updatedAddress.getLayout());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setPincode(updatedAddress.getPincode());
    }

    // Delete the customer, also delete the addresses associated with the customer
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId).orElse(null);
        
        if (customer != null) {
            addressRepository.delete(customer.getCustomerBillingAddress());
            addressRepository.delete(customer.getCustomerShippingAddress());
            
            customerRepository.delete(customer);
        }
    }
}
