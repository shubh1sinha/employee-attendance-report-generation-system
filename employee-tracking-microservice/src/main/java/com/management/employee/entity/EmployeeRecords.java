package com.management.employee.entity;

import java.util.List;


import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document("employee_records")
public class EmployeeRecords {
	
	private long employeeId;
	
	@NonNull
	private String name;
	
	@NonNull
	private String email;
	
	@NonNull
	private String location;
	
	private double billableHours;
	
	private boolean isActive = true;
	
	private List<EmployeeAttendance> employeeAttendance;
}
