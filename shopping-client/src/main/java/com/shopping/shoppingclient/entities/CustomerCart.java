package com.shopping.shoppingclient.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Customer-Cart")
@Entity
public class CustomerCart {
    @Id
    private Long customerId;
    private Long cartId;

    public CustomerCart() {
    }

    public CustomerCart(Long customerId, Long cartId) {
        this.customerId = customerId;
        this.cartId = cartId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "CustomerCart [customerId=" + customerId + ", cartId=" + cartId + "]";
    }

}
