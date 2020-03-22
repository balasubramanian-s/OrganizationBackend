package com.revature.organization.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Faculty;
@Repository
public class FacultyDaoImpl implements FacultyDao {

	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public List<Faculty> get() throws DBException {
		
		Session currentSession=entityManager.unwrap(Session.class);
		Query<Faculty> query=  currentSession.createQuery("from Faculty",Faculty.class);
		List<Faculty> list=query.getResultList();		
		return list;
	}
	
	@Override
	public List<Faculty> getByInstitution(Long id) throws DBException{
		Session currentSession=entityManager.unwrap(Session.class);
		Query<Faculty> query=currentSession.createQuery("from Faculty Where institution_id=" + id,Faculty.class);
		List<Faculty> list=query.getResultList();
		return list;
	}

	@Override
	public Faculty get(Long id)throws DBException {
		Session currentSession=entityManager.unwrap(Session.class);
		Faculty facultyobj=currentSession.get(Faculty.class, id); 	
		return facultyobj;
	}

	@Override
	public void save(Faculty fac) throws DBException{
		Session currentSession=entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(fac);

	}
	

	@Override
	public void delete(Long id)throws DBException {
		Session currentSession=entityManager.unwrap(Session.class);
		Faculty facultyobj=currentSession.get(Faculty.class, id);
		currentSession.delete(facultyobj);

	}

	

}
