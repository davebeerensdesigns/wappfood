package com.wappstars.wappfood;

import com.wappstars.wappfood.config.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileUploadProperties.class
})
public class WappfoodApplication {
	public static void main(String[] args) {
		SpringApplication.run(WappfoodApplication.class, args);
		System.out.println("Application is running");
	}

	// TODO: Use role-based access policies on API-endpoints and double check capabilities for roles
	// TODO: Maybe change endpoints to seperate data more (public/admin)
	// TODO: Exception for adding already existing user role
	// TODO: Check all custom validation for models (maybe use ValidMetaData class)
	// TODO: Add modified by user. (maybe last login date)
	// TODO: Create file uploader/downloader
	// TODO: Add image upload to product, category and user
	// TODO: Create tests for current code (product, category, exceptions)
	// TODO: Build the rest of the application after above is finished (Cart, Tax, TaxClass,ShippingMethods, ShippingZones, PaymentGateway, Order, LineItems, ActionScheduler)
}
