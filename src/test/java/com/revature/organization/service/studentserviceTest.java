package com.revature.organization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.revature.organization.dao.studentdao;
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Organization;
import com.revature.organization.model.student;
class studentserviceTest {

	 @InjectMocks 
	 studentserviceimpl studentservice;
	 
	 @Mock
	studentdao dao;

		@Spy
		List<student> studentList = new ArrayList<student>();

		private int institutionid;

		private int year;

		

		private int id;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
	}

	@Test
	void testGetAllStudent() throws DBException, NotFound {
		List<student> list=new ArrayList<student>();
		Organization org=new Organization();
		org.setId((long) 1);
		String str = "1998-03-13";
		String str2="1999-05-16";
		Date date1 = Date.valueOf(str);
		Date date2=Date.valueOf(str2);
		list.add(new student((long)1,(long)42121,org,"Bala","Subramanian",date1,1,(long)1234567890,"bala@gmail.com"));
		list.add(new student((long)1,(long)42121,org,"Praveen","Kumar",date2,1,(long)1234567890,"praveen@gmail.com"));
		when(dao.get()).thenReturn(list);
		List<student> result=studentservice.getAllStudent();
		assertEquals(list, result);
		verify(dao,times(1)).get();
	}

	@Test
	void testGetStudentById() throws DBException, NotFound {
		Organization org=new Organization();
		org.setId((long) 1);
		String str = "1998-03-13";
		Date date = Date.valueOf(str);
		when(dao.get((long)1)).thenReturn(new student((long)1,(long)42121,org,"Bala","Subramanian",date,1,(long)1234567890,"bala@gmail.com"));
		student result=studentservice.getStudentById((long) 1);
		assertEquals(42121, result.getRedgno());
		assertEquals("Bala", result.getFname());
		assertEquals("bala@gmail.com", result.getEmail());
		
	}

	@Test
	void testGetStudbyInst() throws DBException, NotFound {
		List<student> list=new ArrayList<student>();
		Organization org=new Organization();
		org.setId((long) 2);
		String str = "1998-03-13";
		String str2="1999-05-16";
		Date date1 = Date.valueOf(str);
		Date date2=Date.valueOf(str2);
		list.add(new student((long)1,(long)42121,org,"Bala","Subramanian",date1,1,(long)1234567890,"bala@gmail.com"));
		list.add(new student((long)1,(long)42121,org,"Praveen","Kumar",date2,1,(long)1234567890,"praveen@gmail.com"));
		when(dao.getstudbyInst((long)2)).thenReturn(list);
		List<student> result=studentservice.getStudbyInst((long)2);
		assertEquals(list, result);
		verify(dao,times(1)).getstudbyInst((long) 2);
	}

	@Test
	void testGetStudbyInstYear() throws NotFound, DBException {
		List<student> list=new ArrayList<student>();
		Organization org=new Organization();
		org.setId((long) 1);
		String str = "1998-03-13";
		String str2="1999-05-16";
		Date date1 = Date.valueOf(str);
		Date date2=Date.valueOf(str2);
		list.add(new student((long)1,(long)42121,org,"Bala","Subramanian",date1,1,(long)1234567890,"bala@gmail.com"));
		list.add(new student((long)1,(long)42121,org,"Praveen","Kumar",date2,1,(long)1234567890,"praveen@gmail.com"));
		when(dao.getstudbyInstYear((long)institutionid, year)).thenReturn(list);
		assertNotNull(list);
		assertEquals(studentservice.getStudbyInstYear((long)institutionid, year), list);

	}

	@Test
	void testGetStudbyYear() throws DBException, NotFound {
		List<student> list=new ArrayList<student>();
		Organization org=new Organization();
		org.setId((long) 2);
		String str = "1998-03-13";
		String str2="1999-05-16";
		Date date1 = Date.valueOf(str);
		Date date2=Date.valueOf(str2);
		list.add(new student((long)1,(long)42121,org,"Bala","Subramanian",date1,2,(long)1234567890,"bala@gmail.com"));
		list.add(new student((long)1,(long)42121,org,"Praveen","Kumar",date2,2,(long)1234567890,"praveen@gmail.com"));
		when(dao.getstudbyYear(2)).thenReturn(list);
		List<student> result=studentservice.getStudbyYear(2);
		assertEquals(list, result);
		verify(dao,times(1)).getstudbyYear(2);
	}

	@Test
	void testSaveStudent() throws DBException, BadResponse, NotFound {
		student stud = mock(student.class);
		Organization org=new Organization();
		org.setId((long) 2);
		when(stud.getFname()).thenReturn("ABC");
		when(stud.getLname()).thenReturn("DEF");
		when(stud.getRedgno()).thenReturn((long) 32145678);
		when(stud.getOrg()).thenReturn(org);
		String str = "1998-03-31";
		Date date = Date.valueOf(str);
		when(stud.getDob()).thenReturn(date);
		when(stud.getYear()).thenReturn(3);
		when(stud.getMobileno()).thenReturn((long) 994019744);
		when(stud.getEmail()).thenReturn("abc@gmail.com");
		LocalDateTime cDateTime = LocalDateTime.of(2020, 03, 22, 12, 00, 30);
		when(stud.getCreatedon()).thenReturn(cDateTime);
		LocalDateTime mDateTime = LocalDateTime.of(2020, 03, 22, 12, 05, 45);
		when(stud.getModifiedon()).thenReturn(mDateTime);

		assertEquals("ABC", stud.getFname());
		assertEquals("DEF", stud.getLname());
		assertEquals(32145678, stud.getRedgno());
		assertEquals(org, stud.getOrg());
		assertEquals(date, stud.getDob());
		assertEquals(3, stud.getYear());
		assertEquals(994019744, stud.getMobileno());
		assertEquals("abc@gmail.com", stud.getEmail());
		assertEquals(cDateTime, stud.getCreatedon());
		assertEquals(mDateTime, stud.getModifiedon());

	}

//	@Test
//	void testUpdateStudent() {
//		fail("Not yet implemented");
//	}

	@Test
	void testDeleteStudent() throws DBException {
		student student = new student();
		when(dao.get((long) id)).thenReturn(student);
		assertNotNull(student);
		dao.delete((long) id);
		verify(dao).delete((long) id);
	}

}
