package com.shopping.shoppingclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopping.shoppingclient.entities.CustomerCart;

@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, Long>{

    CustomerCart save(CustomerCart customerCart);

    List<CustomerCart> findAll();

    Optional<CustomerCart> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
    
}
