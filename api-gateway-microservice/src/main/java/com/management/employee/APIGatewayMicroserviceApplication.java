package com.management.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class APIGatewayMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(APIGatewayMicroserviceApplication.class);
	}
}
