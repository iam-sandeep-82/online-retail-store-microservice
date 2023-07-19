package com.cart.cartclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CartClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartClientApplication.class, args);
	}

}
