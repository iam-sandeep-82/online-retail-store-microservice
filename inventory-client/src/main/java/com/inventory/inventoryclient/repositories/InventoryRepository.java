package com.inventory.inventoryclient.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.inventoryclient.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
    List<Inventory> findAll();

    Inventory save(Inventory inventory);
    
    Optional<Inventory> findByInventoryId(Long inventoryId);
    
    Optional<Inventory> findByProductId(Long productId);

    void deleteByInventoryId(Long inventoryId);
}
