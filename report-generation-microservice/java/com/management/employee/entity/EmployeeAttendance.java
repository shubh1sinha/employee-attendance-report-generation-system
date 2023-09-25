package com.management.employee.entity;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmployeeAttendance {

	private String date;
	private DayOfWeek day;
	private Month month;
	private long year;
	private LocalDateTime updatedDateTime;
	private List<InOutRecords> inOutRecords;
	private int totalHours;

}
