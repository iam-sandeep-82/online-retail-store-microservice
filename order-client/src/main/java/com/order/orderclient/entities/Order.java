package com.order.orderclient.entities;

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

@Table(name = "Orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @JsonProperty("LineItems")
    private List<LineItem> items;
    
    public Order() {
    }

    public Order(Long orderId, List<LineItem> items) {
        this.orderId = orderId;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setItems(List<LineItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", items=" + items + "]";
    }
    
    // The orderId will be available after the entity is persisted
    @PostPersist
    private void onPostPersist() {
        System.out.println("Generated orderId: " + orderId);
    }
}
