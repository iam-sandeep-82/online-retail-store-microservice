package com.shopping.shoppingclient.externalClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.shoppingclient.entities.Inventory;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @PostMapping("/api/inventory")
    ResponseEntity<String> addInventory(@RequestBody Inventory inventory);

    @PutMapping("/api/inventory/add")
    public ResponseEntity<String> addInventoryQuantity(@RequestBody Inventory inventory);

    @PutMapping("/api/inventory/subtract")
    public ResponseEntity<String> subtractInventoryQuantity(@RequestBody Inventory inventory);

    @PostMapping("/api/inventory/check")
    public ResponseEntity<String> checkInventoryQuantity(@RequestBody Inventory inventory);
}
