package com.revature.organization.service;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.revature.organization.dao.OrganizationDAO;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Organization;

import com.revature.organization.util.OrganizationMessage;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDAO organizationDAO;

	@Transactional
	@Override
	public List<Organization> get() throws ServiceException {
		List<Organization> org = new ArrayList<Organization>();
		try {
			org = organizationDAO.get();
			if(org.isEmpty()) {
				throw new ServiceException(OrganizationMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return org;
	}

	@Transactional
	@Override
	public Organization get(Long id) throws ServiceException{
		Organization org = new Organization();
		try {
			org = organizationDAO.get(id);
			if(org == null) {
				throw new ServiceException(OrganizationMessage.UNABLE_TO_FIND);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return org;
	}

	@Transactional
	@Override
	public void save(Organization org) throws DBException,MethodArgumentNotValidException{
		try {
			LocalDateTime ts = LocalDateTime.now();	
			if(org.getId()==null) {		
				org.setIsActive(true);
				 org.setCreatedon(ts);
				}
			else {
				org.setModifiedon(ts);
			}
			String name = org.getName();
			String Aname = org.getAlias();
			String University = org.getUniversity();
			if(name == null || Aname == null || University == null) {
				throw new MethodArgumentNotValidException(null, null);
			}
			organizationDAO.save(org);
		}catch(DBException e) {
			System.out.println(e.getMessage());
		} 

	}

	@Transactional
	@Override
	public void delete(Long id)  throws ServiceException{
		Organization org = new Organization();
		try {
			org = organizationDAO.get(id);
			if(org!=null) {
				organizationDAO.delete(id);
			}
			else {
				throw new ServiceException(OrganizationMessage.UNABLE_TO_DELETE);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}
	@Transactional
	@Override
	public void changeStatus(Long id) throws DBException {
		try {
		organizationDAO.changeStatus(id);
		}
		catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}

	

}
