package com.management.employee.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import com.management.employee.entity.EmployeeAttendaceReport;

public class AttendanceRecordRowMapper  implements RowMapper<EmployeeAttendaceReport>{
	
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	DateTimeFormatter formatter4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS");

	@Override
	public EmployeeAttendaceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmployeeAttendaceReport employeeReport = new EmployeeAttendaceReport();
		employeeReport.setEmployeeId(rs.getLong("employee_id"));
		employeeReport.setAttendDate(rs.getString("attend_date"));
		employeeReport.setDay(rs.getString("day"));
		employeeReport.setMonth(rs.getString("month"));
		employeeReport.setYear(rs.getString("year"));
		employeeReport.setFinalReport(rs.getString("final_report"));
		//employeeReport.setUpdatedDateTime(LocalDateTime.parse(rs.getString("updatedDateTime"), formatter4));
		
		return employeeReport;
	}

}
