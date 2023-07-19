package com.inventory.inventoryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryClientApplication.class, args);
	}

}
