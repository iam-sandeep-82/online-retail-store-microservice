package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Inventory {
    
    @JsonProperty("productId")
    private Long productId;
    
    @JsonProperty("quantity")
    private Long quantity;

    public Inventory() {
    }

    public Inventory(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
