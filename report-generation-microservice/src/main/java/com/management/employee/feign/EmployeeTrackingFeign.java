package com.management.employee.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.management.employee.entity.EmployeeRecords;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;

@FeignClient(name = "employee-management", url = "${service.employee-management}")
public interface EmployeeTrackingFeign {
	
	@GetMapping("/employee-tracking/get-employee/{employeeId}")
	public EmployeeRecords getEmployeeRecordByEmployeeId(@PathVariable long employeeId) throws InvalidRequestException, InvalidSearchException;
	
	@PostMapping("/employee-tracking/save/employee")
	public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRecords employeeRecord) throws InvalidRequestException;

}

