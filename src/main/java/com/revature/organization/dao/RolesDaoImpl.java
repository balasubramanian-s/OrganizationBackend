package com.revature.organization.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.query.Query;

import com.revature.organization.exception.DBException;
import com.revature.organization.model.Roles;

@Repository

public class RolesDaoImpl implements RolesDao {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Roles> get(Integer offset,Integer size)  throws DBException{
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Roles> query=currentSession.createQuery("SELECT a FROM Roles a ORDER BY a.name ",Roles.class).setFirstResult(offset).setMaxResults(size);
		List<Roles> list=query.getResultList();
		return list;
	}

	@Override
	public Roles get(Long id)  throws DBException{
		Session currentSession = entityManager.unwrap(Session.class);
		Roles rolesobj=currentSession.get(Roles.class, id);		
		return rolesobj;
	}
	
	@Override
	public void save(Roles role) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(role);
		
		
	}
	
	@Override
	public void delete(Long id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Roles roles=currentSession.get(Roles.class, id);
		currentSession.delete(roles);
		

	}



	
	

}
