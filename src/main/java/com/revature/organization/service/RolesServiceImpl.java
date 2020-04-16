package com.revature.organization.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.dao.RolesDao;
import com.revature.organization.model.Roles;
import com.revature.organization.util.RolesMessage;

import org.springframework.transaction.annotation.Transactional;
@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	private RolesDao rolesDao;
	
	@Transactional
	@Override
	public List<Roles> getRoles(Integer offset,Integer size) throws NotFound {
		List<Roles> list=new ArrayList<Roles>();
		try {
			list=rolesDao.get(offset,size);
			if(list.isEmpty()) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),RolesMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
			
		}
		return list;
	}
	@Transactional
	@Override
	public Roles get(Long id) throws NotFound {
		Roles role = new Roles();
		try {
			role = rolesDao.get(id);
			if(role == null) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),RolesMessage.UNABLE_TO_FIND_ROLE);
			}
		}catch( DBException e) {
			System.out.println(e.getMessage());
		}
		
		return role;
	}
	@Transactional
	@Override
	public void save(Roles role) throws DBException,BadResponse {
		try {
			String name = role.getName();
			if(name == null) {
				throw new BadResponse(HttpStatus.BAD_REQUEST.value(),RolesMessage.UNABLE_TO_INSERT);
			}
			rolesDao.save(role);
			}catch (DBException e) {
				System.out.println(e.getMessage());
			}
		

	}
	@Transactional
	@Override
	public void delete(Long id) throws NotFound {
		Roles role = new Roles();
		try {
			role=rolesDao.get(id);
			if(role == null) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(),RolesMessage.UNABLE_TO_DELETE_ROLE);
				
			}
			rolesDao.delete(id);
			
		}catch( DBException e) {
			System.out.println(e.getMessage());
		}

	}
	

}
