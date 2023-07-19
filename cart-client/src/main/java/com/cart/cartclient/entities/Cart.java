package com.cart.cartclient.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;

@Table(name = "Carts")
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
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

    // To make cartId available after the entity is persisted
    @PostPersist
    private void onPostPersist() {
        
        System.out.println("Generated cartId: " + cartId);
    }
}
