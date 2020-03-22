package com.revature.organization.service;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.model.Faculty;


public interface FacultyService {
	List<Faculty> get() throws ServiceException;
	
	Faculty get(Long id) throws ServiceException;
	
	void save (InsertFacultyDto dto) throws DBException;	
	

	void delete(Long id)throws ServiceException;
	
	List<Faculty> getByInstitution(Long id)throws ServiceException;
	
}
