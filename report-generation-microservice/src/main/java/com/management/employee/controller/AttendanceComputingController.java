package com.management.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.employee.entity.EmployeeAttendaceReport;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.entity.GenericParams;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.service.AttendanceComputingService;
import com.management.employee.service.KafkaConsumerervice;
import com.management.employee.service.KafkaProducerService;

import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;

@RestController
public class AttendanceComputingController {
	
	@Autowired
	private AttendanceComputingService attendanceComputingService;
	
	@Autowired
	private KafkaProducerService kafkaProducerservice;
	
	@Timed(value = "employee_attendance_marking")
	@ApiOperation(value = "API will save employee attendance", response = String.class, produces = "apllication/json")
	@PostMapping("/employee-attendance/mark/{empId}")
	public ResponseEntity<String> markAteendanceForEmployeeByEmployeeId(@PathVariable long empId,@RequestParam(required = true) String action) throws InvalidRequestException, InvalidSearchException{
		
		if(empId >0 && action!=null) {
			return new ResponseEntity<>(attendanceComputingService.saveEmployeeAttendaceByEmployeeId(empId, action), HttpStatus.OK);
		}else{
			throw new InvalidRequestException("Mandatory Inputs are null or not valid!");
		}
	}
	
	@Timed(value = "employee_attendance_report")
	@ApiOperation(value = "API will save employee attendance", response = String.class, produces = "apllication/json")
	@PostMapping("/employee-attendance/generateReport/{empId}")
	public ResponseEntity<EmployeeReport> markAteendanceForEmployeeByEmployeeId(@PathVariable long empId) throws InvalidRequestException, InvalidSearchException{
		
		if(empId >0) {
			return new ResponseEntity<>(kafkaProducerservice.generateEmployeeReportByEmpId(empId), HttpStatus.OK);
		}else{
			throw new InvalidRequestException("Mandatory Inputs are null or not valid!");
		}
	}
	
	@Timed(value = "absent_employee")
	@ApiOperation(value = "List of Absentees Employee", response = String.class, produces = "apllication/json")
	@PostMapping("/employee-attendance/getAbsentEmployee")
	public ResponseEntity<List<EmployeeAttendaceReport>> getAbsenteesEmployeeFromDate(@RequestBody GenericParams genericParam) throws InvalidRequestException, InvalidSearchException{
		Map<String, String> dateRange = new HashMap<>();
		dateRange.put(genericParam.getKey(), genericParam.getRange());
		return new ResponseEntity<>(attendanceComputingService.getAbsentEmployeesByDate(dateRange), HttpStatus.OK);
	}
	

}
