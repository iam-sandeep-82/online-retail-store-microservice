package com.shopping.shoppingclient.externalClients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.shopping.shoppingclient.entities.LineItem;
import com.shopping.shoppingclient.entities.Order;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @PostMapping("/api/order")
    public ResponseEntity<Long> createEmptyOrder();

    @PostMapping("/api/order/{orderId}")
    public ResponseEntity<String> addLineItemsToOrder(@PathVariable Long orderId, @RequestBody List<LineItem> request);

    @GetMapping("/api/order/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId);

    @PutMapping("/api/order/{orderId}")
    public ResponseEntity<String> editOrderDetails(
            @PathVariable Long orderId,
            @RequestBody Order order);
}
