package com.order.orderclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.orderclient.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    
    List<Order> findAll();

    Order save(Order order);
    
    Optional<Order> findById(Long orderId);
    
    void deleteById(Long orderId);
}
