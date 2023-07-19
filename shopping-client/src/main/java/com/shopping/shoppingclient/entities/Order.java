package com.shopping.shoppingclient.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    @JsonProperty("orderId")
    private Long orderId;
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
}
