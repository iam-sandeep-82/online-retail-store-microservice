package com.cart.cartclient.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cart.cartclient.entities.Cart;
import com.cart.cartclient.entities.LineItem;
import com.cart.cartclient.repositories.CartRepository;
import com.cart.cartclient.repositories.LineItemRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    // Save the new cart to the repository
    public Long addCart(Cart cart) {
        Cart savedCart = cartRepository.save(cart);
        return savedCart.getCartId();
    }

    // Add list of LineItems to a cart by Id
    public String addItemsToCart(Long cartId, List<LineItem> lineItems){
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        cart.getItems().addAll(lineItems);
        cartRepository.save(cart);
        return "Items added to the Cart successfully";
    }

    // Save new Item to repository
    public String addLineItem(LineItem lineItem) {
        lineItemRepository.save(lineItem);
        return "Item added successfully.";
    }

    // Get all carts
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    // Retrieve the cart details by product ID from the repository
    public Cart getCartDetails(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    // Update the cart details
    // public void editCartDetails(Long cartId, Cart updatedCart) {
    //     Cart exitingCart = cartRepository.findById(cartId).orElse(null);
        
    //     if (exitingCart != null) {
    //         exitingCart.setItems(updatedCart.getItems());
    //         cartRepository.save(exitingCart);
    //     }
    // }

    // Update the cart details
    public String updateLineItems(Long cartId, List<LineItem> newItems) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<LineItem> existingItems = cart.getItems();

        for (LineItem newItem : newItems) {
            Long newItemProductId = newItem.getProductId();
            LineItem existingItem = getExistingLineItem(existingItems, newItemProductId);
            if (existingItem != null) {
                existingItem.setProductId(newItem.getProductId());
                existingItem.setProductName(newItem.getProductName());
                existingItem.setQuantity(newItem.getQuantity());
            } else {
                existingItems.add(newItem);
            }
        }
        cartRepository.save(cart);
        return "Line items updated successfully in the cart.";
    }

    // helper for updateLineItems, compare the productId's of items
    private LineItem getExistingLineItem(List<LineItem> existingItems, Long productId) {
        for (LineItem item : existingItems) {
            if (item.getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }


    // Method to be used to empty the cart IF REQUIRED
    public void emptyCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // // Delete the cart completely
    // public void deleteCart(Long cartId) {
    //     Cart cart = cartRepository.findById(cartId)
    //         .orElseThrow(() -> new NoSuchElementException("Cart not found"));

    //     List<LineItem> lineItems = cart.getItems();
    //     for (LineItem lineItem : lineItems) {
    //         lineItemRepository.delete(lineItem);            
    //     }
    //     //Use this to delete the cart too
    //     cartRepository.deleteById(cartId);
    // }

    //Delete the cart items only and keep the empty cart
    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException("Cart not found"));

        List<LineItem> lineItems = cart.getItems();
        for (LineItem lineItem : lineItems) {
            lineItemRepository.delete(lineItem);
        }
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // Delele the line item 
    public void deleteLineItem(Long itemId) {
        lineItemRepository.deleteById(itemId);
    }

    // Update line item
    // public LineItem updateLineItem(LineItem lineItem) {
    //     return lineItemRepository.save(lineItem);
    // }

    // Get line item
    public LineItem searchLineItem(Long itemId) {
        return lineItemRepository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("LineItem not found"));
    }
}
