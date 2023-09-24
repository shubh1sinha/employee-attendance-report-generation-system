package com.management.employee.service;


import com.management.employee.entity.EmployeeRecords;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;

public interface EmployeeTrackingServiceInterface {
	
	String saveEmployee(EmployeeRecords empRecords) throws InvalidRequestException;
	
	EmployeeRecords getEmployeeRecordByEmployeeId(long employeeId) throws InvalidSearchException;

}
