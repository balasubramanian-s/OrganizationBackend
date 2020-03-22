package com.revature.organization.service;

import java.util.List;

import com.revature.organization.dto.InsertStudentDto;

import com.revature.organization.model.student;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.exception.DBException;

public interface studentservice {

	public List<student> get() throws ServiceException;

	public student get(Long id) throws ServiceException;

	public void delete(Long id) throws ServiceException;

	public List<student> getstudbyInst(Long institutionid) throws ServiceException;

	public List<student> getstudbyInstYear(Long institutionid, int year) throws ServiceException;

	public List<student> getstudbyYear(int year) throws ServiceException;

	public void save(InsertStudentDto idto) throws DBException;

}
