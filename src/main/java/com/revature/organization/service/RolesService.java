package com.revature.organization.service;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Roles;

public interface RolesService {
	List<Roles>  get() throws ServiceException;
	
	Roles get(Long id) throws ServiceException;
	
	void save(Roles role) throws DBException;
	
	void delete(Long id) throws ServiceException;
	
	
}
