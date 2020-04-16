package com.revature.organization.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.revature.organization.dao.RolesDao;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Roles;

class RolesServiceTest {

	
	 @InjectMocks 
	 RolesServiceImpl roles;
	 
	 @Mock
	 RolesDao dao;
	 
	 @BeforeEach
	    public void init() {
	        MockitoAnnotations.initMocks(this);
	    }
	     
	 
	 private Long id;
	 private Integer offset;
	 private Integer size;
	

	@Test
	void testGet() throws DBException, NotFound {
		List<Roles> list = new ArrayList<Roles>();
		
		list.add(new Roles(1,"HOD"));
		list.add(new Roles(2,"Principal"));
		
		when(dao.get(offset, size)).thenReturn(list);
		
		List<Roles> roleList=roles.getRoles(offset, size);
		 assertEquals(2, roleList.size());
		 verify(dao, times(1)).get(offset, size);
	}

	@Test
	void testGetLong() throws DBException, NotFound {
	when(dao.get((long) 1)).thenReturn(new Roles(1,"HOD"));
	
	Roles role=roles.get((long) 1);
	assertEquals("HOD",role.getName() );
	}

	@Test
	void testSave() throws DBException, BadResponse {
		Roles role=new Roles(1,"Professor");
		roles.save(role);		
		verify(dao, times(1)).save(role);
	}

	@Test
	void testDelete() throws DBException {
		Roles role = new Roles();
		when(dao.get(id)).thenReturn(role);
		assertNotNull(role);
		dao.delete(id);
		verify(dao).delete(id);
	}

}
