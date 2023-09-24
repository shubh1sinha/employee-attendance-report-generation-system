package com.management.employee.service;

import com.management.employee.entity.EmployeeReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;

public interface KafkaConsumerervice {
	 
	void listenToGeneratedReport(String generatedReport) throws InvalidRequestException, InvalidSearchException ;

}
