package com.revature.organization.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.organization.dao.studentdao;
import com.revature.organization.dto.InsertStudentDto;

import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Organization;
import com.revature.organization.model.student;
import com.revature.organization.util.StudentMessage;

@Service
public class studentserviceimpl implements studentservice {

	@Autowired
	private studentdao studdao;

	@Transactional
	@Override
	public List<student> get()throws ServiceException {
		List<student> list = new ArrayList<student>();
		try {
			list= studdao.get();
			if(list.isEmpty()) {
				throw new ServiceException(StudentMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Transactional
	@Override
	public void save(InsertStudentDto idto) throws DBException {
		student stud = new student();
		Organization org = new Organization();
		try {
			if(idto.getId() == null) {
				stud.setCreatedon(idto.getCreatedon());
			}
			else {
				stud=studdao.get(idto.getId());
				stud.setModifiedon(idto.getModifiedon());
				stud.setId(idto.getId());
			}
			org.setId(idto.getInstitutionid());
			stud.setOrg(org);
			stud.setRedgno(idto.getRedgno());
			stud.setFname(idto.getFname());
			stud.setLname(idto.getLname());
			stud.setDob(idto.getDob());
			stud.setYear(idto.getYear());
			stud.setMobileno(idto.getMobileno());
			stud.setEmail(idto.getEmail());
			Long redg = idto.getRedgno();
			String fname = idto.getFname();
			String lname = idto.getLname();
			Date dob = idto.getDob();
			Integer year = idto.getYear();
			Long mobilenumber = idto.getMobileno();
			String email = idto.getEmail();
			if(redg==null || fname==null || lname==null || dob==null || year==null || email==null || mobilenumber==null) {
				throw new DBException(StudentMessage.UNABLE_TO_INSERT);
			}
			studdao.insert(stud);
			
		}catch(DBException e) {
			System.out.println(e.getMessage());			
		}
	}

	@Transactional
	@Override
	public void delete(Long id) throws ServiceException {
		student stud = new student();
		try {
			stud = studdao.get(id);
			if(stud!=null) {
				studdao.delete(id);
			}
			else {
				throw new ServiceException(StudentMessage.UNABLE_TO_DELETE); 
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	@Override
	public List<student> getstudbyInst(Long institutionid) throws ServiceException {
		List<student> stud = new ArrayList<student>();
		try {
			stud = studdao.getstudbyInst(institutionid);
			if(stud.isEmpty()) {
				throw new ServiceException(StudentMessage.NO_STUDENTS_AVAILABLE);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return stud;
	}

	@Transactional
	@Override
	public List<student> getstudbyInstYear(Long institutionid, int year)  throws ServiceException{
		List<student> stud = new ArrayList<student>();
		try {
			stud = studdao.getstudbyInstYear(institutionid,year);
			if(stud.isEmpty()) {
				throw new ServiceException(StudentMessage.NO_STUDENT_YEAR_AVAILABLE);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return stud;
	}

	/*
	 * @Transactional
	 * 
	 * @Override public void update(UpdateDTO udto) { Organization org = new
	 * Organization(); student stud = studdao.get(udto.getId());
	 * System.out.println(stud); org.setId(udto.getInstitutionid());
	 * stud.setId(udto.getId()); stud.setOrg(org); stud.setRedgno(udto.getRedgno());
	 * stud.setFname(udto.getFname()); stud.setLname(udto.getLname());
	 * stud.setDob(udto.getDob()); stud.setYear(udto.getYear());
	 * stud.setMobileno(udto.getMobileno()); stud.setEmail(udto.getEmail());
	 * stud.setModifiedon(udto.getModifiedon()); System.out.println(stud);
	 * studdao.insert(stud); System.out.println(stud); }
	 */
	@Override
	public student get(Long id)  throws ServiceException {
		student stud = new student();
		try {
			stud = studdao.get(id);
			if(stud == null) {
				throw new ServiceException(StudentMessage.UNABLE_TO_FIND);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return stud;
	}

	@Override
	public List<student> getstudbyYear(int year) throws ServiceException {
		List<student> stud = new ArrayList<student>();
		try {
		stud=studdao.getstudbyYear(year);
		if(stud.isEmpty()) {
			throw new ServiceException(StudentMessage.NO_STUDENT_YEAR_AVAILABLE);
		}
	}catch(DBException e) {
		System.out.println(e.getMessage());
	}
	return stud;
	}



	

	

}
