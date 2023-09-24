package com.management.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class EmployeeTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTrackingSystemApplication.class, args);
	}

}
