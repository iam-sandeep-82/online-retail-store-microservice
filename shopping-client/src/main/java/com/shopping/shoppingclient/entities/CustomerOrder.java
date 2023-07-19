package com.shopping.shoppingclient.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Customer-Order")
@Entity
public class CustomerOrder {
    
    private Long customerId;
    @Id
    private Long orderId;
    
    public CustomerOrder() {
    }

    public CustomerOrder(Long customerId, Long orderId) {
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CustomerOrder [customerId=" + customerId + ", orderId=" + orderId + "]";
    }

}
