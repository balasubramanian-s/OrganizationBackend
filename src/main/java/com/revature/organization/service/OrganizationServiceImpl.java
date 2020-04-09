package com.revature.organization.service;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.revature.organization.dao.OrganizationDAO;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;

import com.revature.organization.model.Organization;

import com.revature.organization.util.OrganizationMessage;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDAO organizationDAO;

	
	@Override
	public List<Organization> get() throws NotFound {
		List<Organization> org = new ArrayList<Organization>();
		try {
			org = organizationDAO.get();
			if(org.isEmpty()) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return org;
	}
	@Override
	public List<Organization> getActiveOrganization() throws NotFound {
		List<Organization> org = new ArrayList<Organization>();
		try {
			org = organizationDAO.getActiveOrganization();
			if(org.isEmpty()) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return org;
	}

	
	@Override
	public Organization get(Long id) throws NotFound{
		Organization org =new Organization();
		try {
			org = organizationDAO.get(id);
			if (org==null) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(), OrganizationMessage.NO_RECORD);
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
		return org;
	}

	@Transactional
	@Override
	public void save(Organization org) throws BadResponse, DBException{
		
		LocalDateTime createdon=LocalDateTime.now();
		try {		
			
			if(StringUtils.isBlank(org.getName())|| StringUtils.isBlank(org.getAlias())|| StringUtils.isBlank(org.getUniversity())) {// Checks if a String is not blank, not empty, and not null
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), OrganizationMessage.UNABLE_TO_INSERT);
			}			
			org.setCreatedon(createdon);
			
			organizationDAO.save(org);
		}catch(DBException e) {
			System.out.println(e.getMessage());
		} 

	}
	@Transactional
	@Override
	public void update(Organization org) throws BadResponse, DBException,NotFound {
		Organization orgObj=new Organization();
		LocalDateTime modifiedon=LocalDateTime.now();
		try {
			orgObj=organizationDAO.get(org.getId());
			if(orgObj.getId()==null) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORD);
			}
			if(StringUtils.isBlank(org.getName())||StringUtils.isBlank(org.getAlias())|| StringUtils.isBlank(org.getUniversity())) {// Checks if a String is not blank, not empty, and not null
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), OrganizationMessage.UNABLE_TO_INSERT);
			}
			orgObj.setId(org.getId());
			orgObj.setName(org.getName());
			orgObj.setAlias(org.getAlias());
			orgObj.setUniversity(org.getUniversity());
			orgObj.setModifiedon(modifiedon);
			organizationDAO.save(orgObj);
		
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Transactional
	@Override
	public void delete(Long id)  throws NotFound{
		Organization org =new Organization();
		try {
			org = organizationDAO.get(id);
			if(org!=null) {
				organizationDAO.delete(id);
			}
			else {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),OrganizationMessage.UNABLE_TO_DELETE);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}
	@Transactional
	@Override
	public void changeStatus(Long id) throws DBException,NotFound {
		Organization org = new Organization();
		try {
			org=organizationDAO.get(id);
			if(org==null) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(), OrganizationMessage.NO_RECORD);
			}			
			organizationDAO.changeStatus(id);
		}
		catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}




	

	

}
