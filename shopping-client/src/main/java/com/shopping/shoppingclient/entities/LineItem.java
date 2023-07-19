package com.shopping.shoppingclient.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LineItem {
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("productId")
    private Long productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("quantity")
    private Long quantity;

    public LineItem() {
    }

    public LineItem(Long itemId, Long productId, String productName, Long quantity) {
        this.itemId = itemId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "LineItem [itemId=" + itemId + ", productId=" + productId + ", productName=" + productName
                + ", quantity=" + quantity + "]";
    }

}
