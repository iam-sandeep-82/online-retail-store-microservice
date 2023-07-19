package com.shopping.shoppingclient.externalClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.shoppingclient.entities.Cart;
import com.shopping.shoppingclient.entities.LineItemsRequest;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {
    
    @PostMapping("/api/cart")
    public ResponseEntity<Long> createEmptyCart();

    @PostMapping("/api/cart/{cartId}")
    public ResponseEntity<String> addLineItemsToCart(@PathVariable Long cartId, @RequestBody LineItemsRequest request);

    @PutMapping("/api/cart/{cartId}")
    public ResponseEntity<String> updateLineItemsInCart(@PathVariable Long cartId, @RequestBody LineItemsRequest request);

    @GetMapping("/api/cart/{cartId}")
    public ResponseEntity<Cart> getCartDetails(@PathVariable Long cartId);

    @DeleteMapping("/api/cart/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId);
}
