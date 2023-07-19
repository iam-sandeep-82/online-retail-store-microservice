package com.cart.cartclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cart.cartclient.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    
    List<Cart> findAll();

    Cart save(Cart cart);
    
    Optional<Cart> findById(Long cartId);
    
    void deleteById(Long cartId);
}
