package com.revature.organization.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import static org.mockito.Mockito.mock;
import com.revature.organization.dao.FacultyDaoImpl;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Faculty;
import com.revature.organization.model.Organization;
import com.revature.organization.model.Roles;

class FacultyServiceImplTest {
	@InjectMocks
	private FacultyServiceImpl FacultyServiceImpl;

	@Mock
	FacultyDaoImpl FacultyDao;

	@Spy
	List<Faculty> facultyList = new ArrayList<Faculty>();

	private Long id;

	private Organization org;

	private Roles roles;
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		facultyList = getFacultyList();
	}
	private List<Faculty> getFacultyList() {

		Faculty faculty = new Faculty();

		faculty.setId((long) 1);
		faculty.setEmployee_id(1);
		faculty.setFirst_name("abc");
		faculty.setLast_name("def");
		faculty.setOrg(org);
		faculty.setRoles(roles);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		faculty.setDob(date);
		LocalDateTime cDateTime = LocalDateTime.of(2020, 03, 22, 11, 42, 32);
		faculty.setCreatedon(cDateTime);
		LocalDateTime mDateTime = LocalDateTime.of(2020, 03, 22, 11, 45, 20);
		faculty.setCreatedon(mDateTime);
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);

		facultyList.add(faculty);

		return facultyList;

	}
	@Test
	void testGetAllFaculty() throws NotFound, DBException {
		when(FacultyDao.get()).thenReturn(facultyList);
		assertNotNull(facultyList);
		assertEquals(FacultyServiceImpl.getAllFaculty(), facultyList);
	}

	@Test
	void testGetByFacultyInstitution() throws NotFound, DBException {
		when(FacultyDao.getByInstitution(id)).thenReturn(facultyList);
		assertNotNull(facultyList);
		assertEquals(FacultyServiceImpl.getByFacultyInstitution(id), facultyList);

	}

	@Test
	void testGetFaculty() throws NotFound, DBException {
		Faculty faculty = new Faculty();
		faculty.setFirst_name("abc");
		faculty.setLast_name("def");
		faculty.setOrg(org);
		faculty.setRoles(roles);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		faculty.setDob(date);
		LocalDateTime cDateTime = LocalDateTime.of(2020, 03, 22, 11, 42, 32);
		faculty.setCreatedon(cDateTime);
		LocalDateTime mDateTime = LocalDateTime.of(2020, 03, 22, 11, 45, 20);
		faculty.setCreatedon(mDateTime);
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);

		when(FacultyDao.get(id)).thenReturn(faculty);
		assertNotNull(faculty);
		assertEquals(FacultyServiceImpl.getFaculty(id), faculty);
	}

	@Test
	void testSaveFaculty() {
		Faculty faculty = mock(Faculty.class);

		when(faculty.getFirst_name()).thenReturn("ABC");
		when(faculty.getLast_name()).thenReturn("Kumaar");
		when(faculty.getEmployee_id()).thenReturn(1);
		when(faculty.getOrg()).thenReturn(org);
		when(faculty.getRoles()).thenReturn(roles);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		when(faculty.getDob()).thenReturn(date);
		LocalDateTime cDateTime = LocalDateTime.of(2020, 03, 22, 11, 42, 32);
		when(faculty.getCreatedon()).thenReturn(cDateTime);
		LocalDateTime mDateTime = LocalDateTime.of(2020, 03, 22, 11, 45, 20);
		when(faculty.getModifiedon()).thenReturn(mDateTime);
		when(faculty.getEmail()).thenReturn("abc@gmail.com");
		when(faculty.getMobile_no()).thenReturn((long) 994019744);

		assertEquals("ABC", faculty.getFirst_name());
		assertEquals("Kumaar", faculty.getLast_name());
		assertEquals(1, faculty.getEmployee_id());
		assertEquals(org, faculty.getOrg());
		assertEquals(roles, faculty.getRoles());
		assertEquals(date, faculty.getDob());
		assertEquals(cDateTime, faculty.getCreatedon());
		assertEquals(mDateTime, faculty.getModifiedon());
		assertEquals("abc@gmail.com", faculty.getEmail());
		assertEquals(994019744, faculty.getMobile_no());
	}

//	@Test
//	void testUpdateFaculty() {
//		fail("Not yet implemented");
//	}

	@Test
	void testDeleteFaculty() throws DBException {
		Faculty faculty = new Faculty();
		when(FacultyDao.get(id)).thenReturn(faculty);
		assertNotNull(faculty);
		FacultyDao.delete(id);
		verify(FacultyDao).delete(id);
	}

}
