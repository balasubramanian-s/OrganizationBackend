package com.revature.organization.service;

import java.util.List;
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.model.student;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;

public interface studentservice {

	public List<student> getAllStudent() throws NotFound;

	public student getStudentById(Long id) throws NotFound;

	public List<student> getStudbyInst(Long institutionid) throws NotFound;

	public List<student> getStudbyInstYear(Long institutionid, int year) throws NotFound;

	public List<student> getStudbyYear(int year) throws NotFound;

	public void saveStudent(InsertStudentDto dto) throws DBException,BadResponse;
	
	public void updateStudent(InsertStudentDto dto) throws DBException,BadResponse,NotFound;
	
	public void deleteStudent(Long id) throws NotFound;

	

}
