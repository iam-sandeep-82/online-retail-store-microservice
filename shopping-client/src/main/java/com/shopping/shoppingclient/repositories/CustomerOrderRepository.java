package com.shopping.shoppingclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping.shoppingclient.entities.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>{
    
    CustomerOrder save(CustomerOrder customerOrder);

    List<CustomerOrder> findAll();

    // Had to use the native sql query method as it was creating problem
    @Query("SELECT co.orderId FROM CustomerOrder co WHERE co.customerId = :customerId")
    List<Long> findOrderIdsByCustomerId(@Param("customerId") Long customerId);

    Optional<CustomerOrder> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);
}
