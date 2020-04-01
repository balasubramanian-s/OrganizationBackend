package com.revature.organization.service;

import java.util.List;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;

import com.revature.organization.model.Roles;

public interface RolesService {
	List<Roles>  get() throws NotFound;
	
	Roles get(Long id) throws NotFound;
	
	void save(Roles role) throws DBException,BadResponse;
	
	void delete(Long id) throws NotFound;
	
	
}
