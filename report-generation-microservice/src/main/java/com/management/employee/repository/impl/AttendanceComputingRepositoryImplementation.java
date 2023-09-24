package com.management.employee.repository.impl;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.management.employee.entity.EmployeeAttendaceReport;
import com.management.employee.entity.EmployeeAttendance;
import com.management.employee.entity.EmployeeRecords;
import com.management.employee.entity.EmployeeReport;
import com.management.employee.entity.InOutRecords;
import com.management.employee.enums.AttendanceReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.mapper.AttendanceRecordRowMapper;
import com.management.employee.repository.AttendanceComputingRespository;
import com.management.employee.utility.ServiceUtil;

@Repository
public class AttendanceComputingRepositoryImplementation implements AttendanceComputingRespository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public static final Logger logger = LoggerFactory.getLogger(AttendanceComputingRepositoryImplementation.class);
	
	@Override
	public String saveEmployeeAttendaceByEmployeeId(long employeeId, EmployeeAttendance swipedEmployee, String action,
			boolean ifUpdateRequired) {
		logger.info("saveEmployeeAttendaceByEmployeeId - At Repository Layer");
		Query query = new Query(Criteria.where("employeeId").is(employeeId));
		Update update = new Update();
		if(ifUpdateRequired) {
			update.push("employeeAttendance.$[elem].inOutRecords", ServiceUtil.generateInoInOutRecordsofEmployeeByEmployeeId(employeeId, action));
			update.set("employeeAttendance.$[elem].totalHours", swipedEmployee.getTotalHours());
			update.filterArray(Criteria.where("elem.date").is(swipedEmployee.getDate()));
		}else {
			swipedEmployee.setInOutRecords(new ArrayList<InOutRecords>(Arrays.asList(ServiceUtil.generateInoInOutRecordsofEmployeeByEmployeeId(employeeId, action))));
			update.push("employeeAttendance", swipedEmployee);
		}
		try {
			logger.info("Query for saveEmployeeAttendaceByEmployeeId - "+query.toString());
			mongoTemplate.updateFirst(query, update, EmployeeRecords.class);
		}catch (Exception e) {
			logger.error("Error at repo layer saveEmployeeAttendaceByEmployeeId - "+e.getMessage());
			e.printStackTrace();
			return "Record not updated with employeeId:"+employeeId;
		}
		return "Record updated with employeeId:"+employeeId;
	}

	@Override
	public String saveEveryDayAttendaceRecord(EmployeeReport employeeReport) throws InvalidRequestException {
		logger.info("saveEveryDayAttendaceRecord - At repository level");
		String sql = "INSERT into employee_attendance_report(id, employee_id, attend_date, day, month, year,final_report) values(?,?,?,?,?,?,?)";
		try {
			 jdbcTemplate.update(sql, UUID.randomUUID().hashCode(),
					 employeeReport.getEmployeeRecord().getEmployeeId(), 
						LocalDateTime.now().format(DateTimeFormatter.ISO_DATE),
						LocalDateTime.now().getDayOfWeek().toString(),
						LocalDateTime.now().getMonth().toString(),
						Year.now().getValue(),
						employeeReport.getAttendaceReport()
						);
		}catch(Exception ex){
			logger.error("Error at repository layer saveEveryDayAttendaceRecord - " + ex.getMessage());
			ex.printStackTrace();
			throw new InvalidRequestException("Record not inserted into employee_attendace_report:"+ex.getMessage());
		}
		return "Record inserted into employee_attendace_report";
		 
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<EmployeeAttendaceReport> getAbsentEmployeesByDate(String requestedDate) {
		String sql = "SELECT employee_id, day, year, month,attend_date, final_report FROM employee_attendance_report where "
				+ " final_report = '" + AttendanceReport.ABSENT +"' AND attend_date = '"+requestedDate+"'";
		return jdbcTemplate.query(sql, new Object[] {}, new AttendanceRecordRowMapper());
	}



	
	
	
}
