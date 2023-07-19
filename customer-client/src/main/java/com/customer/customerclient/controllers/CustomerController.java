package com.customer.customerclient.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.customerclient.entities.Customer;
import com.customer.customerclient.entities.CustomerAddress;
import com.customer.customerclient.services.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Get all customers
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    // Add a customer
    @PostMapping("/customers")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        Customer existingCustomer = customerService.findCustomerByEmail(customer.getCustomerEmail());
        if (existingCustomer != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with the same email already exists.");
        }

        CustomerAddress customerAddress1 = customer.getCustomerBillingAddress();
        CustomerAddress customerAddress2 = customer.getCustomerShippingAddress();

        customerService.createAddress(customerAddress1);
        customerService.createAddress(customerAddress2);
        String response = customerService.addCustomer(customer);
        return ResponseEntity.ok(response);
    }
    
    // Get single customer using ID
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerDetails(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    // Get single customer id using email
    @GetMapping("/customers/email/{customerEmail}")
    public ResponseEntity<Long> getCustomerIdByEmail(@PathVariable String customerEmail) {
        //Customer customer = customerService.getCustomerByEmail(customerEmail);
        Long customerId = customerService.getCustomerIdByEmail(customerEmail);
        if (customerId == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customerId);
    }

    // Update a customer using ID
    @PutMapping("/customers/{customerId}")
    public ResponseEntity<String> editCustomerDetails(
            @PathVariable Long customerId,
            @RequestBody Customer customer) {
        
        customerService.editCustomerDetails(customerId, customer);
        return ResponseEntity.ok("Customer details updated successfully.");
    }

    // Delete a particular customer using Id, delete their addresses too
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer and addresses deleted successfully.");
    }
}
