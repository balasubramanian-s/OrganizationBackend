package com.revature.organization.dao;

import java.util.List;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Roles;

public interface RolesDao {
	List<Roles> get() throws DBException;

	Roles get(Long id) throws DBException;

	void save(Roles role) throws DBException;

	void delete(Long id) throws DBException;

}
