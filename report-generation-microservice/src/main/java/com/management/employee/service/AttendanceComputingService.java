package com.management.employee.service;

import java.util.List;
import java.util.Map;

import com.management.employee.entity.EmployeeAttendaceReport;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;

public interface AttendanceComputingService {

	
	String saveEmployeeAttendaceByEmployeeId(long employeeId, String action)
			throws InvalidRequestException, InvalidSearchException;
	
	String saveEveryDayAttendaceRecord(EmployeeReport employeeReport) throws InvalidRequestException;

	List<EmployeeAttendaceReport> getAbsentEmployeesByDate(Map<String, String> dateRange);

}
