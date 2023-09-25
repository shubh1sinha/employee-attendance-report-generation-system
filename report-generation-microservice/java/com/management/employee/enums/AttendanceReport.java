package com.management.employee.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AttendanceReport {
	
	PRESENT("Present"),
	HALF_DAY("Half-Day"),
	ABSENT("ABSENT");
	
	
	private final String displayString;
	
	
	AttendanceReport(String displayString){
		this.displayString =displayString;
	}
	
    public String displayString() {
        return displayString;
    }
    
    @JsonCreator
    public static AttendanceReport fromStringValue(String stringValue) {
        return Arrays.stream(values()).filter(e -> e.displayString.equals(stringValue)).findFirst()
                .orElse(null);
    }

}
