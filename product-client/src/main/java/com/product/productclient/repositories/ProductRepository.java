package com.product.productclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.productclient.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
    List<Product> findAll();

    Product save(Product product);
    
    Optional<Product> findByProductId(Long productId);
    
    void deleteByProductId(Long productId);

    Product findByProductName(String productName);
}
