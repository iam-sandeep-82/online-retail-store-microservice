package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productDescription")
    private String productDescription;

    @JsonProperty("productPrice")
    private Double productPrice;

    @JsonProperty("quantity")
    private Long quantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
