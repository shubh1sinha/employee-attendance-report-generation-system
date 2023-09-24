package com.management.employee.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.management.employee.service.impl.EmployeeTrackingServiceImplemenation;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceUtility {
	
	public static final Logger logger = LoggerFactory.getLogger(EmployeeTrackingServiceImplemenation.class);
	
	public static long generateEmployeeId() {
		logger.info("generateEmployeeId at Utility Leve;");
		long timestamp = System.currentTimeMillis();
		int random = (int)(Math.random()*1000);
		return timestamp*10 + random;
	}

}
