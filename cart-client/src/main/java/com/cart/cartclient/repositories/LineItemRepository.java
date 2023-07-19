package com.cart.cartclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.cartclient.entities.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long>{
    
}
