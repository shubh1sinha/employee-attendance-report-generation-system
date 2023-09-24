package com.management.employee.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.management.employee.entity.EmployeeRecords;
import com.management.employee.exceptions.InvalidRequestException;
import com.management.employee.exceptions.InvalidSearchException;
import com.management.employee.repository.impl.EmployeeTrackingRepositroyImpl;
import com.management.employee.service.impl.EmployeeTrackingServiceImplemenation;

@ExtendWith(MockitoExtension.class)
public class EmployeeTrackingServiceImplTest {
	
	@InjectMocks
	EmployeeTrackingServiceImplemenation employeeTrackingService;
	
	@Mock
	EmployeeTrackingRepositroyImpl employeeTrackingRepository;
	


    @Test
    public void testSaveEmployeeWhenEmployeeIsNotPresent() throws InvalidRequestException {
        EmployeeRecords empRecords = new EmployeeRecords();
        empRecords.setEmployeeId(1);
        empRecords.setName("John");
        when(employeeTrackingRepository.getEmployeeByEmployeeId(empRecords.getEmployeeId())).thenReturn(null);
        when(employeeTrackingRepository.saveEmployee(empRecords)).thenReturn("Employee saved successfully");
        String result = employeeTrackingService.saveEmployee(empRecords);
        Assertions.assertEquals("Employee saved successfully", result);
    }
    
    @Test
    public void testSaveEmployeeWhenEmployeeIsAlreadyPresent() {
        EmployeeRecords empRecords = new EmployeeRecords();
        empRecords.setEmployeeId(1);
        empRecords.setName("John");
        EmployeeRecords presentEmployee = new EmployeeRecords();
        presentEmployee.setEmployeeId(1);
        presentEmployee.setName("John");
        when(employeeTrackingRepository.getEmployeeByEmployeeId(empRecords.getEmployeeId())).thenReturn(presentEmployee);
        try {
            employeeTrackingService.saveEmployee(empRecords);
            Assertions.fail("Expected InvalidRequestException was not thrown");
        } catch (InvalidRequestException e) {
        	Assertions.assertEquals("Employee with empID:" + empRecords.getEmployeeId() + " already Present!", e.getMessage());
        }
    }
    
    @Test
    public void testGetEmployeeRecordByEmployeeIdWhenEmployeeDontExists() throws InvalidSearchException {    long employeeId = 1;
    when(employeeTrackingRepository.getEmployeeByEmployeeId(employeeId)).thenReturn(null);
    try {
        employeeTrackingService.getEmployeeRecordByEmployeeId(employeeId);
        Assertions.fail("Expected InvalidSearchException was not thrown");
    } catch (InvalidSearchException e) {
    	Assertions.assertEquals("Employee Record not found with ID: " + employeeId, e.getMessage());
    }}

    @Test
    public void testGetEmployeeRecordByEmployeeIdWhenEmployeeExists() throws InvalidSearchException {
        long employeeId = 1;
        EmployeeRecords employeeRecords = new EmployeeRecords();
        employeeRecords.setEmployeeId(employeeId);
        employeeRecords.setName("John");
        when(employeeTrackingRepository.getEmployeeByEmployeeId(employeeId)).thenReturn(employeeRecords);
        EmployeeRecords result = employeeTrackingService.getEmployeeRecordByEmployeeId(employeeId);
        Assertions.assertEquals(employeeRecords, result);
    }

}
