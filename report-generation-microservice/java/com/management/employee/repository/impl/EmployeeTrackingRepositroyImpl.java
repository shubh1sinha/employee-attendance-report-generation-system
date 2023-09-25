package com.management.employee.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.management.employee.entity.EmployeeRecords;
import com.management.employee.repository.EmployeeTrackingRepository;
import com.management.employee.service.impl.EmployeeTrackingServiceImplemenation;
import com.management.employee.utility.Constants;

@Repository
public class EmployeeTrackingRepositroyImpl implements EmployeeTrackingRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public static final Logger logger = LoggerFactory.getLogger(EmployeeTrackingServiceImplemenation.class);
	
	@Override
	public String saveEmployee(EmployeeRecords employeeRecords) {
		logger.info("saveEmployee at Repository Layer");
		try {
			logger.info("Saving employee with EmpId:"+employeeRecords.getEmployeeId());
			mongoTemplate.save(employeeRecords, Constants.EMP_RECORD_COLLECTION);
		} catch (Exception e) {
			logger.error("Error while saving the record: "+e.getMessage());
			e.printStackTrace();
			return "Error While Saving records with employeeId " + employeeRecords.getEmployeeId();
		}
		return "Employee with employeeId: " + employeeRecords.getEmployeeId() + " Created";
	}

	@Override
	public EmployeeRecords getEmployeeByEmployeeId(long employeeId){
		logger.info("getEmployeeByEmployeeId at Repository Layer");
		Query query = new Query(Criteria.where("employeeId").is(employeeId));
		return mongoTemplate.findOne(query, EmployeeRecords.class, Constants.EMP_RECORD_COLLECTION);


	}

}
