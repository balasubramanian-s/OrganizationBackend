package com.revature.organization.service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.revature.organization.dao.studentdao;
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Organization;
import com.revature.organization.model.student;
import com.revature.organization.util.FacultyMessage;
import com.revature.organization.util.StudentMessage;

@Service
public class studentserviceimpl implements studentservice {

	@Autowired
	private studentdao studdao;

	
	@Override
	public List<student> getAllStudent()throws NotFound {
		List<student> list = new ArrayList<student>();
		try {
			list= studdao.get();
			if(list.isEmpty()) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	@Transactional
	@Override
	public void saveStudent(InsertStudentDto dto) throws DBException,BadResponse {
		student stud = new student();
		Organization org = new Organization();
		String batchname;
		try {
			if (StringUtils.isBlank(dto.getFname()) || StringUtils.isBlank(dto.getLname())
					|| StringUtils.isBlank(dto.getEmail()) || dto.getRedgno() == 0
					|| dto.getInstitutionid() == null || dto.getDob() == null || dto.getMobileno() == null
					|| dto.getYear() == null) {
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), FacultyMessage.UNABLE_TO_INSERT);
			}
			org.setId(dto.getInstitutionid());
			stud.setOrg(org);
			stud.setRedgno(dto.getRedgno());
			stud.setFname(dto.getFname());
			stud.setLname(dto.getLname());
			stud.setDob(dto.getDob());
			stud.setYear(dto.getYear());
			stud.setMobileno(dto.getMobileno());
			stud.setEmail(dto.getEmail());
			stud.setCreatedon(dto.getCreatedon());
			stud.setBatch(dto.getStartYear()+" - "+ dto.getEndYear());
			studdao.insert(stud);
			
		}catch(DBException e) {
			System.out.println(e.getMessage());			
		}
	}

	@Transactional
	@Override
	public void deleteStudent(Long id) throws NotFound {
		student stud = new student();
		try {
			stud = studdao.get(id);
			if(stud==null) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.UNABLE_TO_DELETE);
				
			}
			studdao.delete(id);
			
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	@Override
	public List<student> getStudbyInst(Long institutionid) throws NotFound {
		List<student> stud = new ArrayList<student>();
		try {
			stud = studdao.getstudbyInst(institutionid);
			if(stud.isEmpty()) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_STUDENTS_AVAILABLE);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return stud;
	}

	@Transactional
	@Override
	public List<student> getStudbyInstYear(Long institutionid, int year)  throws NotFound{
		List<student> stud = new ArrayList<student>();
		try {
			stud = studdao.getstudbyInstYear(institutionid,year);
			if(stud.isEmpty()) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_STUDENT_INSTITUTION_YEAR_AVAILABLE);
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
	public student getStudentById(Long id)  throws NotFound {
		student stud = new student();
		try {
			stud = studdao.get(id);
			if(stud == null) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_RECORD);
			}
		}catch(DBException e) {
			System.out.println(e.getMessage());
		}
		return stud;
	}

	@Override
	public List<student> getStudbyYear(int year) throws NotFound {
		List<student> stud = new ArrayList<student>();
		try {
		stud=studdao.getstudbyYear(year);
		if(stud.isEmpty()) {
			throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_RECORD);
		}
	}catch(DBException e) {
		System.out.println(e.getMessage());
	}
	return stud;
	}
	@Transactional
	@Override
	public void updateStudent(InsertStudentDto dto) throws DBException, BadResponse, NotFound {
		student stud = new student();
		Organization org = new Organization();
		try {
			stud=studdao.get(dto.getId());
			if(stud==null) {
				throw new NotFound(HttpStatus.NO_CONTENT.value(),StudentMessage.NO_RECORD);
			}
			if (StringUtils.isBlank(dto.getFname()) || StringUtils.isBlank(dto.getLname())
					|| StringUtils.isBlank(dto.getEmail()) || dto.getRedgno() == 0
					|| dto.getInstitutionid() == null || dto.getDob() == null || dto.getMobileno() == null
					|| dto.getYear() == null) {
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), FacultyMessage.UNABLE_TO_INSERT);
			}
			org.setId(dto.getInstitutionid());
			stud.setOrg(org);
			stud.setRedgno(dto.getRedgno());
			stud.setFname(dto.getFname());
			stud.setLname(dto.getLname());
			stud.setDob(dto.getDob());
			stud.setYear(dto.getYear());
			stud.setMobileno(dto.getMobileno());
			stud.setEmail(dto.getEmail());
			stud.setModifiedon(dto.getModifiedon());
			studdao.insert(stud);
		}catch(DBException e) {
			System.out.println(e.getMessage());
			
		}
		
	}



	

	

}
