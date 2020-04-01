package com.revature.organization.dao;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Organization;

public interface OrganizationDAO {
	
	public List<Organization> get() throws DBException;
	

	
public	Organization get(Long id)throws DBException;
	
public 	 List<Organization> getActiveOrganization() throws DBException ;
	
	void save(Organization org)throws DBException;
	
	void delete(Long id)throws DBException;
	
	void changeStatus(Long id)throws DBException;
	
}
