package com.management.employee.service.impl;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.management.employee.entity.EmployeeAttendance;
import com.management.employee.entity.EmployeeRecords;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.enums.AttendanceReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.feign.EmployeeTrackingFeign;
import com.management.employee.service.AttendanceComputingService;
import com.management.employee.service.KafkaProducerService;
import com.management.employee.utility.Constants;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService{
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private  EmployeeTrackingFeign employeeTrackingFeign;
	
	@Autowired
	private AttendanceComputingService attendanceComputingService;

	@Override
	public EmployeeReport generateEmployeeReportByEmpId(long empId) throws InvalidRequestException, InvalidSearchException {
		EmployeeReport empReport = new EmployeeReport();
		EmployeeRecords employee = employeeTrackingFeign.getEmployeeRecordByEmployeeId(empId);
		if(employee.getEmployeeAttendance().size()>=1) {
			EmployeeAttendance matchingAttendance = employee.getEmployeeAttendance().stream().filter(i-> i.getDate().equals(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)) && 
					i.getDay().equals(LocalDateTime.now().getDayOfWeek())
					&& i.getMonth().equals(LocalDateTime.now().getMonth()) && i.getYear() == Year.now().getValue()).findFirst().orElse(null);
			
			if(matchingAttendance!=null && matchingAttendance.getTotalHours()>=8) {
				empReport.setAttendaceReport(AttendanceReport.PRESENT.displayString());
			}else if(matchingAttendance!=null && matchingAttendance.getTotalHours()<8 && matchingAttendance.getTotalHours()>=4) {
				empReport.setAttendaceReport(AttendanceReport.HALF_DAY.displayString());
			}else if(matchingAttendance!=null && matchingAttendance.getTotalHours()<4) {
				empReport.setAttendaceReport(AttendanceReport.ABSENT.displayString());
			}else {
				throw new InvalidRequestException("Report Not generated as total hours is not calculated yet!");
			}
			employee.setEmployeeAttendance(new ArrayList<EmployeeAttendance>(Arrays.asList(matchingAttendance)));
			empReport.setEmployeeRecord(employee);
			
		}else {
			throw new InvalidRequestException("Report Not generated!");
		}
		attendanceComputingService.saveEveryDayAttendaceRecord(empReport);
		kafkaTemplate.send(Constants.KAFKA_TOPIC, empReport.getEmployeeRecord().getEmployeeId()+" : " +empReport.getAttendaceReport());
		return empReport;
	}

}
