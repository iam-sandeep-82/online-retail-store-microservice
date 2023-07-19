package com.order.orderclient.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.orderclient.entities.LineItem;
import com.order.orderclient.entities.Order;
import com.order.orderclient.repositories.OrderRepository;



@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    // // Save the new order to the repository
    // public String addOrder(Order order) {
    //     orderRepository.save(order);
    //     return "Order added successfully.";
    // }

    // Save the new order to the repository
    public Long addOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getOrderId();
    }

    // Add items to order
    public String addItemsToOrder(Long orderId, List<LineItem> lineItems){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

        order.getItems().addAll(lineItems);
        orderRepository.save(order);
        return "Items added to the Order successfully";
    }

    // save the order and return its Id
    public Long saveOrderAndGetId(Order order){
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getOrderId();
    }
    
    // Get all orders
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    /** Need to modify this method */
    // Retrieve the order details by product ID from the repository
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    // Update the order details
    public void editOrderDetails(Long orderId, Order updatedOrder) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        
        if (existingOrder != null) {
            existingOrder.setItems(updatedOrder.getItems());
            orderRepository.save(existingOrder);
        }
    }

    // Delete the order
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null) {
            orderRepository.deleteById(orderId);
        }
    }
}
