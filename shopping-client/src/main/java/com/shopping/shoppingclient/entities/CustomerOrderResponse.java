package com.shopping.shoppingclient.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerOrderResponse {
    @JsonProperty("customer")
    private Customer customer;
    @JsonProperty("orders")
    private List<Order> orders;

    public CustomerOrderResponse(Customer customer, List<Order> orders) {
        this.customer = customer;
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
