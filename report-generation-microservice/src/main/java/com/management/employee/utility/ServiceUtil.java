package com.management.employee.utility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.management.employee.entity.InOutRecords;
import com.management.employee.enums.SwipedCase;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceUtil {
	
	public static InOutRecords generateInoInOutRecordsofEmployeeByEmployeeId(long employeeID, String action) {
		InOutRecords record = new InOutRecords();
		if(action!=null && action.equals("true")) {
			record.setSwipedType(SwipedCase.SWIPED_IN.displayString());
		}else if(action!=null && action.equals("false")){
			record.setSwipedType(SwipedCase.SWIPED_OUT.displayString());
		}
		record.setUpdatedTime(LocalTime.now());
		return record;
	}
	
	public static String getDatesFromRange(Map<String,String> key) {
		if(key.containsKey("TODAY")) {
			return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
			
		}else {
			String key1 = key.get("RANGE");
			return key.get("RANGE");
		}
	}

}
