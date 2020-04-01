package com.revature.organization.service;

import java.util.List;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;

import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.model.Faculty;


public interface FacultyService {
	
	List<Faculty> getAllFaculty() throws NotFound ;
	List<Faculty> getByFacultyInstitution(Long id)throws NotFound;
	Faculty getFaculty(Long id) throws NotFound;	
	void saveFaculty (InsertFacultyDto dto) throws BadResponse,DBException;		
	void updateFaculty(InsertFacultyDto dto) throws BadResponse,DBException,NotFound;
	void deleteFaculty(Long id)throws NotFound;
	
	
	
}
