package com.wappstars.wappfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WappfoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(WappfoodApplication.class, args);
		System.out.println("Application is running");
	}

	// TODO: Create better repsonses (and maybe use HATEOS?)
	// TODO: Make all exceptions use the ApiError Class
	// TODO: Set the endpoint restrictions
	// TODO: Extend user to a Customer with more meta data
	// TODO: Add modified by user. (maybe last login date)
	// TODO: Add image upload to product, category and user
	// TODO: Create tests for current code (product, category, exceptions)
	// TODO: Build the rest of the application after above is finished (Cart, Tax, TaxClass,ShippingMethods, ShippingZones, PaymentGateway, Order, LineItems, ActionScheduler)
}
