package com.management.employee.service;

import com.management.employee.entity.EmployeeReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;

public interface KafkaProducerService {
	 
	EmployeeReport generateEmployeeReportByEmpId(long empId) throws InvalidRequestException, InvalidSearchException ;

}
