package com.management.employee.repository;


import com.management.employee.entity.EmployeeRecords;

public interface EmployeeTrackingRepository {
	
	 String saveEmployee(EmployeeRecords employeeRecords);
	 
	 EmployeeRecords getEmployeeByEmployeeId(long employeeId);

}
