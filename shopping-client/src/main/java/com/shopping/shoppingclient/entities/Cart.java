package com.shopping.shoppingclient.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.PostPersist;

public class Cart {
    @JsonProperty("cartId")
    private Long cartId;
    @JsonProperty("LineItems")
    private List<LineItem> items;

    public Cart() {
    }

    public Cart(Long cartId, List<LineItem> items) {
        this.cartId = cartId;
        this.items = items;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", items=" + items + "]";
    }
    
    @PostPersist
    private void onPostPersist() {
        // The cartId will be available after the entity is persisted
        System.out.println("Generated cartId: " + cartId);
        
    }
}
