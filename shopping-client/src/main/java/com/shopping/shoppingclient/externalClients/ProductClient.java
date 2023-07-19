package com.shopping.shoppingclient.externalClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.shoppingclient.entities.Product;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {
    
    @PostMapping("/api/products")
    ResponseEntity<String> addProduct(@RequestBody Product product);

    @GetMapping("/api/products/by-name/{productName}")
    public ResponseEntity<Long> getProductIdByName(@PathVariable String productName);
}
