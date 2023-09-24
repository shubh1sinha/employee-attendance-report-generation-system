package com.management.employee.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;

public enum SwipedCase {
	
	SWIPED_IN("in"),
	SWIPED_OUT("out");
	
	
	private final String displayString;
	
	
	SwipedCase(String displayString){
		this.displayString =displayString;
	}
	
    public String displayString() {
        return displayString;
    }
    
    @JsonCreator
    public static SwipedCase fromStringValue(String stringValue) {
        return Arrays.stream(values()).filter(e -> e.displayString.equals(stringValue)).findFirst()
                .orElse(null);
    }

}
