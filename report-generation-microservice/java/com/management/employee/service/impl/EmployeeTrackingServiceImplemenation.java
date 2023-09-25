package com.management.employee.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.employee.entity.EmployeeRecords;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.repository.EmployeeTrackingRepository;
import com.management.employee.service.EmployeeTrackingServiceInterface;
import com.management.employee.utility.ServiceUtility;

@Service
public class EmployeeTrackingServiceImplemenation implements EmployeeTrackingServiceInterface {

	@Autowired
	private EmployeeTrackingRepository employeeTrackingRepository;

	public static final Logger logger = LoggerFactory.getLogger(EmployeeTrackingServiceImplemenation.class);

	@Override
	public String saveEmployee(EmployeeRecords empRecords) throws InvalidRequestException {

		logger.info("saveEmployee Method at Service Level");
		EmployeeRecords presentEmployee = employeeTrackingRepository
				.getEmployeeByEmployeeId(empRecords.getEmployeeId());
		if (presentEmployee == null) {
			logger.info("No Employee Present Ctreating record for EmployeeName" + empRecords.getName());
			empRecords.setEmployeeId(ServiceUtility.generateEmployeeId());
			return employeeTrackingRepository.saveEmployee(empRecords);
		} else {
			logger.error("Employee Already Present");
			throw new InvalidRequestException(
					"Employee with empID:" + empRecords.getEmployeeId() + " already Present!");
		}

	}

	@Override
	public EmployeeRecords getEmployeeRecordByEmployeeId(long employeeId) throws InvalidSearchException {
		logger.info("getEmployeeRecordByEmployeeId at Service Layer");
		EmployeeRecords employeeRecords = employeeTrackingRepository.getEmployeeByEmployeeId(employeeId);
		if (employeeRecords == null) {
			logger.error("Employee Not Found");
			throw new InvalidSearchException("Employee Record not found with ID: " + employeeId);
		} else {
			logger.info("Emplioyee Found with EmpId" + employeeId);
			return employeeRecords;
		}
	}

}
