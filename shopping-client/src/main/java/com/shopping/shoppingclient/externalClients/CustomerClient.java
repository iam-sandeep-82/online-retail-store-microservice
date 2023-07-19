package com.shopping.shoppingclient.externalClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.shoppingclient.entities.Customer;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {
    
    @PostMapping("/api/customers")
    ResponseEntity<String> addCustomer(@RequestBody Customer customer);

    @GetMapping("/api/customers/email/{customerEmail}")
    ResponseEntity<Long> getCustomerIdByEmail(@PathVariable String customerEmail);

    @GetMapping("/api/customers/{customerId}")
    ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId);
}
