package com.cart.cartclient.controllers;

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

import com.cart.cartclient.entities.Cart;
import com.cart.cartclient.entities.LineItem;
import com.cart.cartclient.entities.LineItemsRequest;
import com.cart.cartclient.services.CartService;

@RestController
@RequestMapping("/api")
public class CartController {
    
    @Autowired
    private CartService cartService;

    // Add a cart
    // @PostMapping("/cart")
    // public ResponseEntity<String> addCart(@RequestBody Cart cart) {
    //     String response = cartService.addCart(cart);
    //     return ResponseEntity.ok(response);
    // }

    // Add a empty cart
    @PostMapping("/cart")
    public ResponseEntity<Long> createEmptyCart() {
        Cart cart = new Cart();
        Long cartId = cartService.addCart(cart);
        return ResponseEntity.ok(cartId);
    }
    
    // // Add items to a cart
    // @PostMapping("/cart/{cartId}")
    // public ResponseEntity<String> addLineItemsToCart(@PathVariable Long cartId, @RequestBody List<LineItem> lineItems) {
    //     String response = cartService.addItemsToCart(cartId, lineItems);
    //     return ResponseEntity.ok(response);
    // }

    // Adding items to the cart of given ID
    @PostMapping("/cart/{cartId}")
    public ResponseEntity<String> addLineItemsToCart(@PathVariable Long cartId, @RequestBody LineItemsRequest request) {
        List<LineItem> lineItems = request.getLineItems();
        String response = cartService.addItemsToCart(cartId, lineItems);
        return ResponseEntity.ok(response);
    }
    
    //Get all carts
    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCarts(){
        List<Cart> carts = cartService.getCarts();
        if (carts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carts);
    }
    
    // Get single cart using ID
    @GetMapping("/cart/{cartId}")
    public ResponseEntity<Cart> getCartDetails(@PathVariable Long cartId) {
        Cart cart = cartService.getCartDetails(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    // Get only the items of a cart of given ID
    @GetMapping("/cart/{cartId}/items")
    public ResponseEntity<List<LineItem>> getCartItems(@PathVariable Long cartId){
        Cart cart = cartService.getCartDetails(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        List<LineItem> items = cart.getItems();
        return ResponseEntity.ok(items);

    }

    // Update a cart using ID
    @PutMapping("/cart/{cartId}")
    //public ResponseEntity<String> updateLineItemsInCart(@PathVariable Long cartId, @RequestBody List<LineItem> newItems) {
    public ResponseEntity<String> updateLineItemsInCart(@PathVariable Long cartId, @RequestBody LineItemsRequest request) {
        List<LineItem> lineItems = request.getLineItems();
        String response = cartService.updateLineItems(cartId, lineItems);
        return ResponseEntity.ok(response);
    }

    // Delete a cart using ID
    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok("Emptied the Cart successfully.");
    }
}
