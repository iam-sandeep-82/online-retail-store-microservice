package com.product.productclient.controllers;

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

import com.product.productclient.entities.Product;
import com.product.productclient.services.ProductService;



@RestController
@RequestMapping("/api")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    // Add a product
    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        String response = productService.addProduct(product);
        return ResponseEntity.ok(response);
    }
    
    //Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }
    
    // Get single product using ID
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductDetails(@PathVariable Long productId) {
        Product product = productService.getProductDetails(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    // Get the productId by product name
    @GetMapping("/products/by-name/{productName}")
    public ResponseEntity<Long> getProductIdByName(@PathVariable String productName) {
        Long productId = productService.getProductIdByName(productName);
        return ResponseEntity.ok(productId);
    }

    // Update a product using ID
    @PutMapping("/products/{productId}")
    public ResponseEntity<String> editProductDetails(
            @PathVariable Long productId,
            @RequestBody Product product) {
        
        productService.editProductDetails(productId, product);
        return ResponseEntity.ok("Product details updated successfully.");
    }

    // Delete a product using ID
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
