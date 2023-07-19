package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    @JsonProperty("productName")
    private String productName;
 
    @JsonProperty("productDescription")
    private String productDescription;
    
    @JsonProperty("productPrice")
    private Double productPrice;

    public Product() {
    }

    public Product(String productName, String productDescription, Double productPrice) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }


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

}
