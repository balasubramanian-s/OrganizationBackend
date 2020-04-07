package com.revature.organization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.revature.organization.dao.OrganizationDAO;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Organization;


class OrganizationServiceTest {

	 @InjectMocks 
	 OrganizationServiceImpl organization;
	 
	 @Mock
	 OrganizationDAO dao;
	
	 @Spy
		List<Organization> organizationList = new ArrayList<Organization>();
	 private Long id;
	@BeforeEach
	void setUp() throws Exception {
		   MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGet() throws DBException, NotFound {
		List<Organization> list = new ArrayList<Organization>();
		list.add(new Organization(1,"KarpagaVinayaga","KVEG","Anna University",true));
		list.add(new Organization(2,"Aruna College of Engineering", "ACET","Anna University",true));
		list.add(new Organization(3, "IF", "IFET"," Anna University",true));
		
		when(dao.get()).thenReturn(list);
		
		List<Organization> org=organization.get();
		assertEquals(3, org.size());
		verify(dao,times(1)).get();
	}

	@Test
	void testGetActiveOrganization() throws DBException, NotFound {
		List<Organization> list = new ArrayList<Organization>();
		list.add(new Organization(1,"KarpagaVinayaga","KVEG","Anna University",true));
		list.add(new Organization(2,"Aruna College of Engineering", "ACET","Anna University",true));
		list.add(new Organization(3, "IF", "IFET"," Anna University",true));
		when(dao.getActiveOrganization()).thenReturn(list);
		List <Organization> org=organization.getActiveOrganization();
		
		verify(dao,times(1)).getActiveOrganization();
		assertEquals(list, org);
	}

	@Test
	void testGetLong() throws DBException, NotFound {
		when(dao.get((long) 1)).thenReturn(new Organization(1,"KarpagaVinayaga","KVEG","Anna University",true));
		Organization org=organization.get((long) 1);
		assertEquals("KarpagaVinayaga", org.getName());
	}

	@Test
	void testSave() throws BadResponse, DBException {
		Organization org=new Organization(4,"Jeppiar","JCET","Anna University",true);
		organization.save(org);
		verify(dao,times(1)).save(org);
	
		
	}

	@Test
	void testUpdate() throws BadResponse, DBException, NotFound {
		when(dao.get((long) 4)).thenReturn(new Organization(4,"Jeppiar","JCET","Anna University",true));

		Organization org=organization.get((long) 4);	
		org.setName("Jeppiar College");
		organization.update(org);
		assertEquals("Jeppiar College", org.getName());
		verify(dao,times(1)).save(org);
	}

	@Test
	void testDelete() throws DBException {
		Organization organization = new Organization();
		when(dao.get(id)).thenReturn(organization);
		assertNotNull(organization);
		dao.delete(id);
		verify(dao).delete(id);
		
	}

	@Test
	void testChangeStatus() throws DBException, NotFound {		
		Organization org = new Organization();
		org.setIsActive(true);
		when(dao.get(id)).thenReturn(org);
		assertEquals(organization.get(id), org);
		
		
} 
	

}
