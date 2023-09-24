package com.management.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.management.employee.entity.EmployeeRecords;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.service.EmployeeTrackingServiceInterface;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;

@RestController
public class EmployeeTrackingRestController {
	
	@Autowired
	private EmployeeTrackingServiceInterface employeeTrackingServiceInterface;
	
	@ApiOperation(value = "API will save employee records", response = String.class, produces = "apllication/json")
	@PostMapping("/employee-tracking/save/employee")
	public ResponseEntity<String> saveEmployee(@RequestBody EmployeeRecords employeeRecord) throws InvalidRequestException{
		if(employeeRecord != null && employeeRecord.getEmail() !=null && employeeRecord.getName() !=null) {
			return new ResponseEntity<>(employeeTrackingServiceInterface.saveEmployee(employeeRecord), HttpStatus.OK);
		}else {
			throw new InvalidRequestException("Mandatory Inputs cannot be null");
		}
		
	}
	
	@ApiOperation(value = "API will give employee records by Id", response = EmployeeRecords.class, produces = "apllication/json")
	@GetMapping("/employee-tracking/get-employee/{employeeId}")
	public ResponseEntity<EmployeeRecords> getEmployeeRecordByEmployeeId(@PathVariable long employeeId) throws InvalidRequestException, InvalidSearchException{
		if(employeeId>0) {
			return new ResponseEntity<>(employeeTrackingServiceInterface.getEmployeeRecordByEmployeeId(employeeId), HttpStatus.OK);
		}else {
			throw new InvalidRequestException("Mandatory Inputs cannot be null or 0");
		}
		
	}

}
