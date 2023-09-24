package com.management.employee.entity;


import java.time.LocalTime;

import com.management.employee.enums.SwipedCase;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InOutRecords {
	
	private LocalTime updatedTime;
	private String swipedType;

}
