package com.revature.organization.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;

import com.revature.organization.dao.FacultyDao;
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.model.Faculty;
import com.revature.organization.model.Organization;
import com.revature.organization.model.Roles;
import com.revature.organization.util.FacultyMessage;
import com.revature.organization.util.OrganizationMessage;

import org.apache.commons.lang3.StringUtils;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	private FacultyDao facultyDao;

	
	@Override
	public List<Faculty> getAllFaculty() throws NotFound {
		List<Faculty> list = new ArrayList<Faculty>();
		try {
			list = facultyDao.get();
			if (list.isEmpty()) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(), FacultyMessage.NO_RECORD);
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	
	@Override
	public Faculty getFaculty(Long id) throws NotFound {
		Faculty fac = new Faculty();
		try {
			fac = facultyDao.get(id);
			if (fac == null) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(), FacultyMessage.UNABLE_TO_FIND);
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());

		}
		return fac;
	}

	@Transactional
	@Override
	public void saveFaculty(InsertFacultyDto dto) throws BadResponse, DBException {
		Faculty fac = new Faculty();
		Organization org = new Organization();
		Roles role = new Roles();
		try {
			
			if (StringUtils.isBlank(dto.getFirst_name()) || StringUtils.isBlank(dto.getLast_name())
					|| StringUtils.isBlank(dto.getEmail()) || dto.getEmployee_id() == 0
					|| dto.getInstitution_id() == null || dto.getDob() == null || dto.getMobile_no() == null
					|| dto.getRole_id() == null) {
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), FacultyMessage.UNABLE_TO_INSERT);
			}
		
			fac.setCreatedon(dto.getCreatedon());
			fac.setEmployee_id(dto.getEmployee_id());
			org.setId(dto.getInstitution_id());
			fac.setOrg(org);
			fac.setFirst_name(dto.getFirst_name());
			fac.setLast_name(dto.getLast_name());
			fac.setDob(dto.getDob());
			fac.setEmail(dto.getEmail());
			fac.setMobile_no(dto.getMobile_no());
			role.setId(dto.getRole_id());
			fac.setRoles(role);

			facultyDao.save(fac);

		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void deleteFaculty(Long id) throws NotFound {
		Faculty fac = new Faculty();
		try {
			fac = facultyDao.get(id);
			if (fac != null) {
				facultyDao.delete(id);
			} else {
				throw new NotFound(HttpStatus.NOT_FOUND.value(), FacultyMessage.UNABLE_TO_DELETE);
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<Faculty> getByFacultyInstitution(Long id) throws NotFound {
		List<Faculty> list = new ArrayList<Faculty>();
		try {
			list = facultyDao.getByInstitution(id);
			if (list.isEmpty()) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(), FacultyMessage.NO_FAULTY_AVAILABLE);
			}
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
		return list;

	}
	@Transactional
	@Override
	public void updateFaculty(InsertFacultyDto dto) throws BadResponse, DBException, NotFound {
		Faculty fac = new Faculty();
		Organization org = new Organization();
		Roles role = new Roles();
		try {
			fac = facultyDao.get(dto.getId());
			if (fac == null) {
				throw new NotFound(HttpStatus.NOT_FOUND.value(), FacultyMessage.UNABLE_TO_FIND);
			}

			if (StringUtils.isBlank(dto.getFirst_name()) || StringUtils.isBlank(dto.getLast_name())
					|| StringUtils.isBlank(dto.getEmail()) || dto.getEmployee_id() == 0
					|| dto.getInstitution_id() == null || dto.getDob() == null || dto.getMobile_no() == null
					|| dto.getRole_id() == null) {
				throw new BadResponse(HttpStatus.NOT_ACCEPTABLE.value(), FacultyMessage.UNABLE_TO_INSERT);
			}
			fac.setEmployee_id(dto.getEmployee_id());
			org.setId(dto.getInstitution_id());
			fac.setOrg(org);
			fac.setFirst_name(dto.getFirst_name());
			fac.setLast_name(dto.getLast_name());
			fac.setDob(dto.getDob());
			fac.setEmail(dto.getEmail());
			fac.setMobile_no(dto.getMobile_no());
			fac.setModifiedon(dto.getModifiedon());
			role.setId(dto.getRole_id());
			fac.setRoles(role);
			facultyDao.save(fac);

		} catch (DBException e) {
			System.out.println(e.getMessage());
		}

	}

}
