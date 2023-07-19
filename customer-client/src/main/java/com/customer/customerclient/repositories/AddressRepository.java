package com.customer.customerclient.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customer.customerclient.entities.CustomerAddress;

@Repository
public interface AddressRepository extends JpaRepository<CustomerAddress, Long>{

    CustomerAddress save(CustomerAddress address);
    
    Optional<CustomerAddress> findById(Long addressId);
    
    void deleteByAddressId(Long addressId);
}
