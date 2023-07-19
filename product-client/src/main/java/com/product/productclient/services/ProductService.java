package com.product.productclient.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.productclient.entities.Product;
import com.product.productclient.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    // Save the new product to the repository
    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product added successfully.";
    }

    // Get all products
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Retrieve the product details by product ID from the repository
    public Product getProductDetails(Long productId) {
        return productRepository.findByProductId(productId).orElse(null);
    }

    // Get a product ID by its name
    public Long getProductIdByName(String productName) {
        Product product = productRepository.findByProductName(productName);
        if (product == null) {
            //throw new NoSuchElementException("Product not found");
            return null;
        }
        return product.getProductId();
    }

    // Update the product details
    public void editProductDetails(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findByProductId(productId).orElse(null);
        
        if (existingProduct != null) {
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductDescription(updatedProduct.getProductDescription());
            existingProduct.setProductPrice(updatedProduct.getProductPrice());
            productRepository.save(existingProduct);
        }
    }

    // Delete the product
    public void deleteProduct(Long productId) {
        Product product = productRepository.findByProductId(productId).orElse(null);

        if (product != null) {
            productRepository.deleteByProductId(productId);
        }
    }
}
