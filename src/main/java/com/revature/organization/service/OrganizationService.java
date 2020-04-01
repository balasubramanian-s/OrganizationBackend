package com.revature.organization.service;

import java.util.List;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Organization;

public interface OrganizationService {

	
	public List<Organization> get() throws NotFound;

	public Organization get(Long id)throws NotFound;
	
	public List<Organization> getActiveOrganization() throws NotFound ;
	
	void save(Organization dto)throws BadResponse,DBException;
	void update(Organization dto)throws BadResponse,DBException,NotFound;
	
	void delete(Long id)throws NotFound;
	
	void changeStatus(Long id)throws DBException,NotFound;
}
