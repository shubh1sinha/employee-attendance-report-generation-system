package com.management.employee.repository;


import java.util.List;

import com.management.employee.entity.EmployeeAttendaceReport;
import com.management.employee.entity.EmployeeAttendance;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.exceptions.InvalidRequestException;

public interface AttendanceComputingRespository {
	
	String saveEmployeeAttendaceByEmployeeId(long employeeId, EmployeeAttendance swipedEmployee, String action,boolean ifUpdateRequired);
	
	String saveEveryDayAttendaceRecord(EmployeeReport employeeReport) throws InvalidRequestException;

	List<EmployeeAttendaceReport> getAbsentEmployeesByDate(String requestedDate);


}
