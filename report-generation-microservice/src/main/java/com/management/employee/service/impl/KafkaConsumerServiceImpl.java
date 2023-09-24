package com.management.employee.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.management.employee.entity.EmployeeReport;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.service.KafkaConsumerervice;
import com.management.employee.utility.Constants;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerervice {

	@Override
	@KafkaListener(topics = Constants.KAFKA_TOPIC, groupId = "attendance_report")
	public void listenToGeneratedReport(String generatedReport)
			throws InvalidRequestException, InvalidSearchException {
		System.out.println(generatedReport);
	}

}
