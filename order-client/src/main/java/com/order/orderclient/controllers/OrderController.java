package com.order.orderclient.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.orderclient.entities.LineItem;
import com.order.orderclient.entities.Order;
import com.order.orderclient.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    // Add a empty order
    @PostMapping("/order")
    public ResponseEntity<Long> createEmptyOrder() {
        Order order = new Order();
        Long orderId = orderService.addOrder(order);
        return ResponseEntity.ok(orderId);
    }
    
    // Add items to the order using Id
    @PostMapping("/order/{orderId}")
    public ResponseEntity<String> addLineItemsToOrder(@PathVariable Long orderId, @RequestBody List<LineItem> request) {
        //List<LineItem> lineItems = request.getLineItems();
        String response = orderService.addItemsToOrder(orderId, request);
        return ResponseEntity.ok(response);
    }

    //Get all order
    @GetMapping("/order")
    public ResponseEntity<List<Order>> getOrders(){
        List<Order> orders = orderService.getOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }
    
    // Get single order using ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetails(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // Update a order using ID
    @PutMapping("/order/{orderId}")
    public ResponseEntity<String> editOrderDetails(
            @PathVariable Long orderId,
            @RequestBody Order order) {
        
        orderService.editOrderDetails(orderId, order);
        return ResponseEntity.ok("Order details updated successfully.");
    }

    // Delete a order using ID
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully.");
    }
}
