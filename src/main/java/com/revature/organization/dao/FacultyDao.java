package com.revature.organization.dao;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Faculty;

public interface FacultyDao {

	List<Faculty> get() throws DBException;

	Faculty get(Long id) throws DBException;

	void save(Faculty fac) throws DBException;

	void delete(Long id) throws DBException;

	List<Faculty> getByInstitution(Long id) throws DBException;

}
