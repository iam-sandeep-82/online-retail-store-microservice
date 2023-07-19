package com.customer.customerclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.customerclient.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
    Customer save(Customer customer);

    List<Customer> findAll();
    
    Optional<Customer> findByCustomerId(Long customerId);

    Optional<Customer> findByCustomerEmail(String customerEmail);

    //Long findCustomerIdByCustomerEmail(String email);
    
    void deleteByCustomerId(Long customerId);
    
    //void deleteAll();
}

