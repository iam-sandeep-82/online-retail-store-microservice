package com.inventory.inventoryclient.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="Inventories")
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    @Column(name = "productId")
    private Long productId;
    @Column(name = "quantity")
    private Long quantity;

    public Inventory() {
    }

    public Inventory(Long inventoryId, Long productId, Long quantity) {
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
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

    @Override
    public String toString() {
        return "Inventory [inventoryId=" + inventoryId + ", productId=" + productId + ", quantity=" + quantity + "]";
    }

}
