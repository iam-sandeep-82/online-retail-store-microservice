package com.shopping.shoppingclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingclient.entities.Customer;
import com.shopping.shoppingclient.entities.CustomerOrderResponse;
import com.shopping.shoppingclient.entities.LineItemsRequest;
import com.shopping.shoppingclient.entities.ProductRequest;
import com.shopping.shoppingclient.services.ShoppingService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/api/shoppingservice")
public class ShoppingController {
    
    @Autowired
    private ShoppingService shoppingService;

    @PostMapping("/products")
    @CircuitBreaker(name =  "productInventoryBreaker", fallbackMethod = "productInventoryFallback")
    public ResponseEntity<String> createProductAndInventory(@RequestBody ProductRequest productRequest) {
        return shoppingService.createProductAndInventory(productRequest);
    }
   
    //fallback method 
    public ResponseEntity<String> productInventoryFallback(ProductRequest productRequest, Exception exception){
        // String errorMessage = "Fallback is executed because product or inventory service is down: " + exception.getMessage();
        // return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @PostMapping("/customer")
    @CircuitBreaker(name = "customerCartBreaker", fallbackMethod = "customerCartFallback")
    public ResponseEntity<String> createCustomerAndCart(@RequestBody Customer customer){
        return shoppingService.createCustomerAndCart(customer);
    }

    //fallback method
    public ResponseEntity<String> customerCartFallback(Customer customer, Exception exception) {
        // String errorMessage = "Fallback is executed because customer or cart service is down: " + exception.getMessage();
        // return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @PutMapping("/customer/{customerId}/cart")
    @CircuitBreaker(name = "customerCartBreaker", fallbackMethod = "addProductsToCartFallback")
    public ResponseEntity<String> addProductsToCart(@RequestBody LineItemsRequest lineItemsRequest, 
                                    @PathVariable Long customerId){
        Long cartId = shoppingService.getCustomerCart(customerId);
        if(cartId != null){
            return shoppingService.addProductsToCart(cartId, lineItemsRequest);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //fallback method
    public ResponseEntity<String> addProductsToCartFallback(LineItemsRequest lineItemsRequest, Long customerId, Exception exception) {
        // String errorMessage = "Fallback is executed because customer or cart service is down: " + exception.getMessage();   
        // return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @PostMapping("/customer/{customerId}/order")
    @CircuitBreaker(name = "customerOrderBreaker", fallbackMethod = "addProductsToOrderFallback")
    public ResponseEntity<String> addProductsToOrder(@PathVariable Long customerId){
        return shoppingService.createCustomerAndOrder(customerId);
    }

    //fallback
    public ResponseEntity<String> addProductsToOrderFallback(Long customerId, Exception exception) {
        // String errorMessage = "Fallback is executed because order service is down: " + exception.getMessage();
        // return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(exception.getMessage());
    }

    @GetMapping("/customer/{customerId}/orders")
    @CircuitBreaker(name = "customerOrderBreaker", fallbackMethod = "getAllOrdersForCustomerFallback")
    public ResponseEntity<CustomerOrderResponse> getAllOrdersForCustomer(@PathVariable Long customerId) {
        // CustomerOrderResponse response = shoppingService.getAllOrdersForCustomer(customerId);
        // if (response == null) {
        //     return ResponseEntity.notFound().build();
        // }
        // return ResponseEntity.ok(response);
        return shoppingService.getAllOrdersForCustomer(customerId);
    }

    public ResponseEntity<CustomerOrderResponse> getAllOrdersForCustomerFallback(Long customerId, Exception exception) {
        //String errorMessage = "Fallback is executed because customer or order service is down: " + exception.getMessage();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
    }
}
