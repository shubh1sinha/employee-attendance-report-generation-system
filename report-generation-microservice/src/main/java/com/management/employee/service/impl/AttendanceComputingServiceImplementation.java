package com.management.employee.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.employee.entity.EmployeeAttendaceReport;
import com.management.employee.entity.EmployeeAttendance;
import com.management.employee.entity.EmployeeRecords;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.entity.InOutRecords;
import com.management.employee.enums.SwipedCase;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.feign.EmployeeTrackingFeign;
import com.management.employee.repository.AttendanceComputingRespository;
import com.management.employee.service.AttendanceComputingService;
import com.management.employee.utility.ServiceUtil;

@Service
public class AttendanceComputingServiceImplementation implements AttendanceComputingService {

	@Autowired
	private AttendanceComputingRespository attendanceComputingRespository;

	@Autowired
	private EmployeeTrackingFeign employeeTrackingFeign;
	
	public static final Logger logger = LoggerFactory.getLogger(AttendanceComputingServiceImplementation.class);

	@Override
	public String saveEmployeeAttendaceByEmployeeId(long employeeId, String action)
			throws InvalidRequestException, InvalidSearchException {
		logger.info("saveEmployeeAttendaceByEmployeeId - at service layer");
		EmployeeRecords employee = employeeTrackingFeign.getEmployeeRecordByEmployeeId(employeeId);
		Boolean ifUpdateRequired = false;
		EmployeeAttendance swipedEmployee = new EmployeeAttendance();
		if (employee != null && employee.getEmployeeAttendance() == null) {
			logger.info("saveEmployeeAttendaceByEmployeeId - Employee Find with empId:"+employeeId);
			swipedEmployee.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
			swipedEmployee.setDay(LocalDateTime.now().getDayOfWeek());
			swipedEmployee.setMonth(LocalDateTime.now().getMonth());
			swipedEmployee.setYear(Year.now().getValue());
			swipedEmployee.setUpdatedDateTime(LocalDateTime.now());
			swipedEmployee.setTotalHours(0);
		} else if (employee.getEmployeeAttendance().size() > 0) {
			EmployeeAttendance matchingAttendance = employee.getEmployeeAttendance().stream()
					.filter(i -> i.getDate().equals(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
							&& i.getDay().equals(LocalDateTime.now().getDayOfWeek())
							&& i.getMonth().equals(LocalDateTime.now().getMonth())
							&& i.getYear() == Year.now().getValue())
					.findFirst().orElse(null);
			if (matchingAttendance == null) {
				swipedEmployee.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
				swipedEmployee.setDay(LocalDateTime.now().getDayOfWeek());
				swipedEmployee.setMonth(LocalDateTime.now().getMonth());
				swipedEmployee.setYear(Year.now().getValue());
				swipedEmployee.setUpdatedDateTime(LocalDateTime.now());
			} else {
				swipedEmployee = matchingAttendance;
				swipedEmployee.setUpdatedDateTime(LocalDateTime.now());
				ifUpdateRequired = true;
				if (action.equals("false")) {
					matchingAttendance.getInOutRecords()
							.add(ServiceUtil.generateInoInOutRecordsofEmployeeByEmployeeId(employeeId, action));
					swipedEmployee.setTotalHours((int) calculateTotalWorkingHours(employeeId, matchingAttendance));
				}
			}
		} else {
			logger.error("saveEmployeeAttendaceByEmployeeId - Error At Service Level");
			throw new InvalidRequestException("The request triggered is invalid!");
		}
		return attendanceComputingRespository.saveEmployeeAttendaceByEmployeeId(employeeId, swipedEmployee, action,
				ifUpdateRequired);
	}

	public long calculateTotalWorkingHours(long employeeId, EmployeeAttendance matchingAttendance)
			throws InvalidRequestException {
		List<InOutRecords> inRecords = matchingAttendance.getInOutRecords().stream()
				.filter(i -> i.getSwipedType().equals(SwipedCase.SWIPED_IN.displayString()))
				.collect(Collectors.toList());

		List<InOutRecords> outRecords = matchingAttendance.getInOutRecords().stream()
				.filter(i -> i.getSwipedType().equals(SwipedCase.SWIPED_OUT.displayString()))
				.collect(Collectors.toList());

		if (inRecords.size() != outRecords.size()) {
			throw new InvalidRequestException("Swipe-In and Swipe-Out dosent' match hence it's not calculated");
		}
		long totalWorkingHours = 0;
		for (int i = 0; i < inRecords.size(); i++) {
			LocalTime swipeIn = inRecords.get(i).getUpdatedTime();
			LocalTime swipeOut = outRecords.get(i).getUpdatedTime();
			Duration duration = Duration.between(swipeOut, swipeIn);
			totalWorkingHours += duration.toHours();
		}
		return totalWorkingHours;
	}

	@Override
	public String saveEveryDayAttendaceRecord(EmployeeReport employeeReport) throws InvalidRequestException {
		return attendanceComputingRespository.saveEveryDayAttendaceRecord(employeeReport);
	}

	@Override
	public List<EmployeeAttendaceReport> getAbsentEmployeesByDate(Map<String, String> dateRange) {
		String getDatesFromKey = ServiceUtil.getDatesFromRange(dateRange);
		return attendanceComputingRespository.getAbsentEmployeesByDate(getDatesFromKey);
	}

}
