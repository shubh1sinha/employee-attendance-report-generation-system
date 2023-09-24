package com.management.employee.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table(name = "employee_attendace_report")
public class EmployeeAttendaceReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "employee_id")
	private long employeeId;
	
	@Column(name="attend_date")
	private String attendDate;

	@Column(name = "day")
	private String day;

	@Column(name = "month")
	private String month;

	@Column(name = "year")
	private String year;

	@Column(name = "updatedDateTime")
	private LocalDateTime updatedDateTime;

	@Column(name = "final_report")
	private String finalReport;

}
