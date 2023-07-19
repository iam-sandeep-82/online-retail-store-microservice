package com.shopping.shoppingclient.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.shopping.shoppingclient.entities.Cart;
import com.shopping.shoppingclient.entities.Customer;
import com.shopping.shoppingclient.entities.CustomerCart;
import com.shopping.shoppingclient.entities.CustomerOrder;
import com.shopping.shoppingclient.entities.CustomerOrderResponse;
import com.shopping.shoppingclient.entities.Inventory;
import com.shopping.shoppingclient.entities.LineItem;
import com.shopping.shoppingclient.entities.LineItemsRequest;
import com.shopping.shoppingclient.entities.Order;
import com.shopping.shoppingclient.entities.Product;
import com.shopping.shoppingclient.entities.ProductRequest;
import com.shopping.shoppingclient.externalClients.CartClient;
import com.shopping.shoppingclient.externalClients.CustomerClient;
import com.shopping.shoppingclient.externalClients.InventoryClient;
import com.shopping.shoppingclient.externalClients.OrderClient;
import com.shopping.shoppingclient.externalClients.ProductClient;
import com.shopping.shoppingclient.repositories.CustomerCartRepository;
import com.shopping.shoppingclient.repositories.CustomerOrderRepository;

@Service
public class ShoppingService {
    
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private ProductClient productClient;
    @Autowired 
    private CartClient cartClient;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private CustomerCartRepository customerCartRepository;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    //Method to create product and inventory and perform other checks
    // public ResponseEntity<String> createProductAndInventory(ProductRequest productRequest) {
    //     // Shopping service calling Product service to create a product
    //     Product product = new Product();
    //     product.setProductName(productRequest.getProductName());
    //     product.setProductDescription(productRequest.getProductDescription());
    //     product.setProductPrice(productRequest.getProductPrice());

    //     ResponseEntity<String> productResponse = productClient.addProduct(product);
    //     if (productResponse.getStatusCode().is2xxSuccessful()) {
    //         // using the getProductIdByName method from shopping service
    //         ResponseEntity<Long> productIdResponse = productClient.getProductIdByName(product.getProductName());
    //         if (productIdResponse.getStatusCode().is2xxSuccessful()) {
    //             Long productId = productIdResponse.getBody();

    //             // shopping service calls Inventory service to update inventory
    //             Inventory inventory = new Inventory();
    //             inventory.setProductId(productId);
    //             inventory.setQuantity(productRequest.getQuantity());

    //             ResponseEntity<String> inventoryResponse = inventoryClient.addInventory(inventory);
    //             if (inventoryResponse.getStatusCode().is2xxSuccessful()) {
    //                 return ResponseEntity.status(HttpStatus.CREATED).body("Product and Inventory created Successfully!");
    //             } else {
    //                 // For failure to update inventory
    //                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the inventory.");
    //             }
    //         } else {
    //             // For failure to retrieve productId
    //             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve productId.");
    //         }
    //     } else {
    //         // For failure to create product
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product.");
    //     }
    // }

    //Method to create product and inventory and perform other checks
    public ResponseEntity<String> createProductAndInventory(ProductRequest productRequest) {
        ResponseEntity<Long> productIdResponse = productClient.getProductIdByName(productRequest.getProductName());

        if (productIdResponse.getStatusCode().is2xxSuccessful()) {
            Long productId = productIdResponse.getBody();

            if (productId == null) {
                // if the product doesn't exist, we create a new one
                Product product = new Product();
                product.setProductName(productRequest.getProductName());
                product.setProductDescription(productRequest.getProductDescription());
                product.setProductPrice(productRequest.getProductPrice());

                ResponseEntity<String> productResponse = productClient.addProduct(product);
                if (!productResponse.getStatusCode().is2xxSuccessful()) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product.");
                }

                // to get the ID of the new product we have created
                ResponseEntity<Long> newProductIdResponse = productClient.getProductIdByName(product.getProductName());
                if (newProductIdResponse.getStatusCode().is2xxSuccessful()) {
                    productId = newProductIdResponse.getBody();
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve productId.");
                }
            }
            // here we use update so if inventory is already present it adds quantity
            Inventory inventory = new Inventory();
            inventory.setProductId(productId);
            inventory.setQuantity(productRequest.getQuantity());

            ResponseEntity<String> inventoryResponse = inventoryClient.addInventoryQuantity(inventory);
            if (inventoryResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Product and Inventory created Successfully!");
            } else {
                // For failure to update inventory
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the inventory.");
            }
        } else {
            // For failure to retrieve productId
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve productId.");
        }
    }


    // Method to create Customer and empty cart and also perform some checks
    public ResponseEntity<String> createCustomerAndCart(Customer customer){
        ResponseEntity<String> customerResponse = customerClient.addCustomer(customer);
        CustomerCart customerCart = new CustomerCart();
        if (customerResponse.getStatusCode().is2xxSuccessful()) {
            // using the getProductIdByName method from the shopping service class
            ResponseEntity<Long> customerIdResponse = customerClient.getCustomerIdByEmail(customer.getCustomerEmail());
            if (customerIdResponse.getStatusCode().is2xxSuccessful()) {
                Long customerId = customerIdResponse.getBody();
                customerCart.setCustomerId(customerId);

                ResponseEntity<Long> cartResponse = cartClient.createEmptyCart();
                if (cartResponse.getStatusCode().is2xxSuccessful()) {
                    Long cartId = cartResponse.getBody();
                    //Long cartId = extractCartIdFromResponseBody(cartResponseBody);
                    customerCart.setCartId(cartId);
                    customerCartRepository.save(customerCart);
                    return ResponseEntity.status(HttpStatus.CREATED).body("Customer and Cart created successfully");
                } else {
                    // For failure to update inventory
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create cart.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get customerId.");
            }
        } else {
            // For failure to create product
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create customer.");
        }
    }

    // just a helper method to get cartId of particular customer using customerId
    public Long getCustomerCart(Long customerId) {
        Optional<CustomerCart> optionalCustomerCart = customerCartRepository.findByCustomerId(customerId);
        if (optionalCustomerCart.isPresent()) {
            CustomerCart customerCart = optionalCustomerCart.get();
            return customerCart.getCartId();
        } else {
            return null;
        }
    }

    // Adding products to empty cart or cart that has items in it
    public ResponseEntity<String> addProductsToCart(Long cartId, LineItemsRequest lineItemsRequest){
        ResponseEntity<Cart> cart = cartClient.getCartDetails(cartId);
        if (cart.getStatusCode().is2xxSuccessful()) {
            Cart existingCart = cart.getBody();
            List<LineItem> items = existingCart.getItems();
            if (items != null) {
                ResponseEntity<String> cartResponse = cartClient.updateLineItemsInCart(cartId, lineItemsRequest);
                if (cartResponse.getStatusCode().is2xxSuccessful()) {
                    //Long cartId = extractCartIdFromResponseBody(cartResponseBody);
                    return ResponseEntity.ok().body("Products added to cart successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the cart.");
                }
            } else {
                ResponseEntity<String> cartResponse = cartClient.addLineItemsToCart(cartId, lineItemsRequest);
                if (cartResponse.getStatusCode().is2xxSuccessful()) {
                    //Long cartId = extractCartIdFromResponseBody(cartResponseBody);
                    return ResponseEntity.ok().body("Products added to cart successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the cart.");
                }
            }
        } else {
            // For failure to create product
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve cart details.");
        }
    }

    // public List<Long> getAllOrderIdsByCustomerId(Long customerId) {
    //     return customerOrderRepository.findAllOrderIdsByCustomerId(customerId);
    // }

    // Method to create a order and entry in customer-order table
    // public ResponseEntity<String> createCustomerAndOrder(Long customerId) {
    //     CustomerOrder customerOrder = new CustomerOrder();
    //     customerOrder.setCustomerId(customerId);
        
    //     Long cartId = getCustomerCart(customerId);
        
    //     ResponseEntity<Cart> cartResponse = cartClient.getCartDetails(cartId);
        
    //     if (cartResponse.getStatusCode().is2xxSuccessful()) {
    //         Cart existingCart = cartResponse.getBody();
    //         List<LineItem> cartItems = existingCart.getItems();
            
    //         //ResponseEntity<Order> orderResponse = orderClient.addOrderUsingList(cartItems);
    //         ResponseEntity<Long> orderResponse = orderClient.createEmptyOrder();
    //         if (orderResponse.getStatusCode().is2xxSuccessful()) {
    //             Long newOrderId = orderResponse.getBody();

    //             customerOrder.setOrderId(newOrderId);
    //             customerOrderRepository.save(customerOrder);
    //             ResponseEntity<String> addItemsToOrder = orderClient.addLineItemsToOrder(newOrderId, cartItems);
    //             if(addItemsToOrder.getStatusCode().is2xxSuccessful()){
    //                 ResponseEntity<String> deleteCartResponse = cartClient.deleteCart(cartId);
                
    //                 if (deleteCartResponse.getStatusCode().is2xxSuccessful()) {
    //                     for(LineItem item : cartItems){
    //                         Inventory newInventory = new Inventory();
    //                         newInventory.setProductId(item.getProductId());
    //                         newInventory.setQuantity(item.getQuantity());
    //                         ResponseEntity<String> subtractResponse = inventoryClient.subtractInventoryQuantity(newInventory);
    //                         if(!subtractResponse.getStatusCode().is2xxSuccessful()){
    //                             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Insufficient inventory quantity");
    //                         }
    //                     }
    //                     return ResponseEntity.ok().body("Customer and Order created successfully!");
    //                 } else {
    //                     // For failure to delete cart
    //                     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the cart.");
    //                 }
    //             }else{
    //                 // For failure to add item to order
    //                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to order.");
    //             }
    //         } else {
    //             // For failure to create order
    //             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order.");
    //         }
    //     } else {
    //         // For failure to retrieve cart details
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve cart detailss.");
    //     }
    // }

    public ResponseEntity<String> createCustomerAndOrder(Long customerId) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customerId);

        Long cartId = getCustomerCart(customerId);

        ResponseEntity<Cart> cartResponse = cartClient.getCartDetails(cartId);

        if (cartResponse.getStatusCode().is2xxSuccessful()) {
            Cart existingCart = cartResponse.getBody();
            List<LineItem> cartItems = existingCart.getItems();

            for (LineItem item : cartItems) {
                Inventory inventory = new Inventory();
                inventory.setProductId(item.getProductId());
                inventory.setQuantity(item.getQuantity());

                ResponseEntity<String> inventoryResponse = inventoryClient.checkInventoryQuantity(inventory);
                if (!inventoryResponse.getStatusCode().is2xxSuccessful()) {
                    // Handle insufficient inventory quantity
                    return ResponseEntity.badRequest().body("Not enough quantity present in inventory");
                }
            }

            ResponseEntity<Long> orderResponse = orderClient.createEmptyOrder();
            if (orderResponse.getStatusCode().is2xxSuccessful()) {
                Long newOrderId = orderResponse.getBody();

                customerOrder.setOrderId(newOrderId);
                customerOrderRepository.save(customerOrder);
                ResponseEntity<String> addItemsToOrder = orderClient.addLineItemsToOrder(newOrderId, cartItems);
                if (addItemsToOrder.getStatusCode().is2xxSuccessful()) {
                    ResponseEntity<String> deleteCartResponse = cartClient.deleteCart(cartId);

                    if (deleteCartResponse.getStatusCode().is2xxSuccessful()) {
                        for (LineItem item : cartItems) {
                            Inventory newInventory = new Inventory();
                            newInventory.setProductId(item.getProductId());
                            newInventory.setQuantity(item.getQuantity());
                            inventoryClient.subtractInventoryQuantity(newInventory);
                        }
                        return ResponseEntity.ok().body("Customer and Order created successfully!");
                    } else {
                        // For failure to delete cart
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the cart.");
                    }
                } else {
                    // For failure to add item to order
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add item to order.");
                }
            } else {
                // For failure to create order
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order.");
            }
        } else {
            // For failure to retrieve cart details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve cart details.");
        }
    }


    //Method to return all the orders of a customer and return a object which has details of customer and orders in it
    public ResponseEntity<CustomerOrderResponse> getAllOrdersForCustomer(Long customerId) {
        List<Long> orderIds = customerOrderRepository.findOrderIdsByCustomerId(customerId);
        if (orderIds.isEmpty()) {
            return ResponseEntity.notFound().build(); // Customer not found in the table, return null or handle accordingly
        }

        // using the customer service methods to get customer details
        ResponseEntity<Customer> customerResponse = customerClient.getCustomerById(customerId);
        if (!customerResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.notFound().build(); // Unable to retrieve customer details, return null or handle accordingly
        }
        Customer customer = customerResponse.getBody();

        // using the order service methods to get all orders for the customer
        List<Order> orders = new ArrayList<>();
        for (Long orderId : orderIds) {
            ResponseEntity<Order> orderResponse = orderClient.getOrderDetails(orderId);
            if (orderResponse.getStatusCode().is2xxSuccessful()) {
                Order order = orderResponse.getBody();
                orders.add(order);
            }
        }

        CustomerOrderResponse response = new CustomerOrderResponse(customer, orders);
        return ResponseEntity.ok(response);
    }
}

