package com.inventory.inventoryclient.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.inventoryclient.entities.Inventory;
import com.inventory.inventoryclient.services.InventoryService;


@RestController
@RequestMapping("/api")
public class InventoryController {
    
    @Autowired
    private InventoryService inventoryService;

    // Add a inventory
    @PostMapping("/inventory")
    public ResponseEntity<String> addInventory(@RequestBody Inventory inventory) {
        String response = inventoryService.addInventory(inventory);
        return ResponseEntity.ok(response);
    }
    
    //Get all inventories
    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getAllInventories(){
        List<Inventory> inventories = inventoryService.getInventories();
        if (inventories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventories);
    }
    
    // Get single inventory using ID
    @GetMapping("/inventory/{inventoryId}")
    public ResponseEntity<Inventory> getInventoryDetails(@PathVariable Long inventoryId) {
        Inventory inventory = inventoryService.getInventoryDetails(inventoryId);
        if (inventory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventory);
    }

    // Update a inventory using ID
    @PutMapping("/inventory/{inventoryId}")
    public ResponseEntity<String> editInventoryDetails(
            @PathVariable Long inventoryId,
            @RequestBody Inventory inventory) {
        
        inventoryService.editInventoryDetails(inventoryId, inventory);
        return ResponseEntity.ok("Inventory details updated successfully.");
    }

    // Update the quantity of inventory items when customer's order is processed
    @PutMapping("/inventory/add")
    public ResponseEntity<String> addInventoryQuantity(@RequestBody Inventory inventory) {
        inventoryService.updateInventoryQuantity(inventory, false);
        return ResponseEntity.ok("Inventory quantity updated successfully.");
    }

    @PutMapping("/inventory/subtract")
    public ResponseEntity<String> subtractInventoryQuantity(@RequestBody Inventory inventory) {
        inventoryService.updateInventoryQuantity(inventory, true);
        return ResponseEntity.ok("Inventory quantity updated successfully.");
    }
    
    // Delete a inventory using ID
    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.ok("Inventory deleted successfully.");
    }

    @PostMapping("/inventory/check")
    public ResponseEntity<String> checkInventoryQuantity(@RequestBody Inventory inventory) {
        ResponseEntity<String> response = inventoryService.checkInventoryQuantity(inventory);
        return response;
    }
}
