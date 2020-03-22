package com.revature.organization.service;

import java.util.List;

import com.revature.organization.exception.ServiceException;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Organization;

public interface OrganizationService {

	
	List<Organization> get() throws ServiceException;

	Organization get(Long id)throws ServiceException;
	
	void save(Organization org)throws DBException;
	
	void delete(Long id)throws ServiceException;
	
	void changeStatus(Long id)throws DBException;
}
