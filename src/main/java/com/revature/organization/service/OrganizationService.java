package com.revature.organization.service;

import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.revature.organization.exception.ServiceException;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.model.Organization;

public interface OrganizationService {

	
	List<Organization> get() throws ServiceException;

	Organization get(Long id)throws ServiceException;
	
	void save(Organization org)throws DBException,MethodArgumentNotValidException;
	
	void delete(Long id)throws ServiceException;
	
	void changeStatus(Long id)throws DBException;
}
