package com.inventory.inventoryclient.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inventory.inventoryclient.entities.Inventory;
import com.inventory.inventoryclient.repositories.InventoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepository;

    // Save the new inventory to the repository
    public String addInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
        return "Inventory added successfully.";
    }

    // Get all inventories
    public List<Inventory> getInventories() {
        return inventoryRepository.findAll();
    }

    // Retrieve the inventory details by product ID from the repository
    public Inventory getInventoryDetails(Long inventoryId) {
        return inventoryRepository.findByInventoryId(inventoryId).orElse(null);
    }

    // Update quantity of Inventory object if customer orders some products
    // public void updateInventoryQuantity(Inventory inventory) {
    //     Inventory existingInventory = inventoryRepository.findByProductId(inventory.getProductId()).orElse(null);

    //     if (existingInventory != null) {
    //         Long orderedQuantity = inventory.getQuantity();
    //         Long availableQuantity = existingInventory.getQuantity();

    //         if (availableQuantity >= orderedQuantity) {
    //             Long updatedQuantity = availableQuantity - orderedQuantity;
    //             existingInventory.setQuantity(updatedQuantity);
    //             inventoryRepository.save(existingInventory);
    //         } else {
    //             
    //         }
    //     } else {
    //         
    //     }
    // }

    public ResponseEntity<String> updateInventoryQuantity(Inventory inventory, boolean subtractQuantity) {
        Inventory existingInventory = inventoryRepository.findByProductId(inventory.getProductId()).orElse(null);

        if (existingInventory != null) {
            Long orderedQuantity = inventory.getQuantity();
            Long availableQuantity = existingInventory.getQuantity();

            if (subtractQuantity) {
                if (availableQuantity >= orderedQuantity) {
                    Long updatedQuantity = availableQuantity - orderedQuantity;
                    existingInventory.setQuantity(updatedQuantity);
                    inventoryRepository.save(existingInventory);
                    return ResponseEntity.ok("Inventory quantity updated successfully.");
                } else {
                    return ResponseEntity.badRequest().body("Insufficient inventory quantity");
                }
            } else {
                Long updatedQuantity = availableQuantity + orderedQuantity;
                existingInventory.setQuantity(updatedQuantity);
                inventoryRepository.save(existingInventory);
                return ResponseEntity.ok("Inventory quantity updated successfully.");
            }
        } else {
            inventoryRepository.save(inventory);
            return ResponseEntity.ok("Inventory added successfully.");
        }
    }

    // Update the inventory details
    public void editInventoryDetails(Long inventoryId, Inventory updatedInventory) {
        Inventory existingInventory = inventoryRepository.findByInventoryId(inventoryId).orElse(null);
        
        if (existingInventory != null) {
            existingInventory.setProductId(updatedInventory.getProductId());
            existingInventory.setQuantity(updatedInventory.getQuantity());
            inventoryRepository.save(existingInventory);
        }
    }

    // Delete the inventory
    public void deleteInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId).orElse(null);

        if (inventory != null) {
            inventoryRepository.deleteByInventoryId(inventoryId);
        }
    }

    // check the quantity of item in inventory
    public ResponseEntity<String> checkInventoryQuantity(Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findByProductId(inventory.getProductId()).orElse(null);

        if (existingInventory != null) {
            Long availableQuantity = existingInventory.getQuantity();
            Long requestedQuantity = inventory.getQuantity();

            if (availableQuantity >= requestedQuantity) {
                return ResponseEntity.ok("Sufficient quantity available in inventory.");
            } else {
                return ResponseEntity.badRequest().body("Insufficient inventory quantity.");
            }
        } else {
            return ResponseEntity.badRequest().body("Inventory not found.");
        }
    }
}
