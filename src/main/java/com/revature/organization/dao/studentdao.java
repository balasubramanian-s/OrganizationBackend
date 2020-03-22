package com.revature.organization.dao;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.student;

public interface studentdao {

	public List<student> get()  throws DBException;
	
	public student get(Long id) throws DBException;
	
	public void insert(student stud) throws DBException;

	public  void delete(Long id) throws DBException;

	public List<student> getstudbyInst(Long institutionid) throws DBException;

	public List<student> getstudbyInstYear(Long institutionid, int year) throws DBException;
	
	public List<student> getstudbyYear(int year) throws DBException;

	public void update(student stud) throws DBException;

		
		

}
