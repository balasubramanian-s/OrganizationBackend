package com.revature.organization.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Organization;
import com.revature.organization.model.Roles;
import com.revature.organization.model.student;
import com.revature.organization.service.studentservice;

class StudentControllerTest {
	private MockMvc mockmvc;

	private ObjectMapper om = new ObjectMapper();

	@InjectMocks
	StudentController studentController;

	@Mock
	studentservice studentService;

	@Spy
	List<student> studList = new ArrayList<student>();

	private Organization org;

	private Roles roles;

	private int id;

	private int instid;

	private int year;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(studentController).build();
		studList = getStudList();
	}

	private List<student> getStudList() {
		student stud = new student();

		stud.setId((long) 1);
		stud.setOrg(org);
		stud.setRedgno((long) 310816);
		stud.setFname("ABC");
		stud.setLname("DEF");
		String str = "1998-03-31";
		Date date = Date.valueOf(str);
		stud.setDob(date);
		stud.setYear(4);
		stud.setMobileno((long) 994016369);
		stud.setEmail("abc@gmail.com");

		studList.add(stud);
		return studList;
	}

	@Test
	void testGetAllStudent() throws Exception {
		when(studentService.getAllStudent()).thenReturn(studList);
		this.mockmvc.perform(get("/student/")).andExpect(status().isOk());
	}

	@Test
	void testGetAllStudentExpectFailure() throws Exception {
		doThrow(NotFound.class).when(studentService).getAllStudent();
		this.mockmvc.perform(get("/student/")).andExpect(status().isNotFound());
	}

	@Test
	void testGetStudentById() throws Exception {
		student stud = new student();
		stud.setId((long) 1);
		stud.setOrg(org);
		stud.setRedgno((long) 310816);
		stud.setFname("ABC");
		stud.setLname("DEF");
		String str = "1998-03-31";
		Date date = Date.valueOf(str);
		stud.setDob(date);
		stud.setYear(4);
		stud.setMobileno((long) 994016369);
		stud.setEmail("abc@gmail.com");
		when(studentService.getStudentById((long) id)).thenReturn(stud);
		this.mockmvc.perform(get("/student/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	void testGetStudentByIdExpectFailure() throws Exception {
		id = 1;
		doThrow(NotFound.class).when(studentService).getStudentById((long) id);
		this.mockmvc.perform(get("/student/{id}", 1)).andExpect(status().isNotFound());
	}

	@Test
	void testGetStudentByInst() throws Exception {
		when(studentService.getStudbyInst((long) instid)).thenReturn(studList);
		this.mockmvc.perform(get("/student/institution/{institutionid}", 1)).andExpect(status().isOk());
	}

	@Test
	void testGetstudbyInstExpectFailure() throws Exception {
		instid = 1;
		doThrow(NotFound.class).when(studentService).getStudbyInst((long) instid);
		this.mockmvc.perform(get("/student/institution/{institutionid}", 1)).andExpect(status().isNotFound());
	}

	@Test
	void testGetStudentByInstYear() throws Exception {
		when(studentService.getStudbyInstYear((long) instid, year)).thenReturn(studList);
		this.mockmvc.perform(get("/student/institution/year/{institutionid}/{year}", 1, 1)).andExpect(status().isOk());
	}

	@Test
	void testGetstudbyInstYearExpectFailure() throws Exception {
		instid = 1;
		year = 1;
		doThrow(NotFound.class).when(studentService).getStudbyInstYear((long) instid, year);
		this.mockmvc.perform(get("/student/institution/year/{institutionid}/{year}", 1, 1))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetStudentByYear() throws Exception {
		when(studentService.getStudbyYear(year)).thenReturn(studList);
		this.mockmvc.perform(get("/student/year/{year}", 1)).andExpect(status().isOk());

	}
	@Test
	void testGetstudbyYearExpectFailure() throws Exception {
		year = 1;
		doThrow(NotFound.class).when(studentService).getStudbyYear(year);
		this.mockmvc.perform(get("/student/year/{year}", 1)).andExpect(status().isNotFound());
	}
	@Test
	void testSaveStudent() throws Exception {
		InsertStudentDto stud=new InsertStudentDto();
		stud.setId((long)1);
		stud.setInstitutionid((long) 1);
		stud.setRedgno((long) 310816);
		stud.setFname("ABC");
		stud.setLname("DEF");
		String str = "1998-03-31";
		Date date = Date.valueOf(str);
		stud.setDob(date);
		stud.setYear(4);
		stud.setMobileno((long) 994016369);
		stud.setEmail("abc@gmail.com");
		doNothing().when(studentService).saveStudent(stud);
		String orgJson = om.writeValueAsString(stud);
		MvcResult result = this.mockmvc
				.perform(post("/student/").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andExpect(status().isCreated()).andReturn();

	}
	@Test
	void testSaveExpectFailure() throws Exception {
		InsertStudentDto stud = new InsertStudentDto();
		doThrow(BadResponse.class).when(studentService).saveStudent(stud);
		this.mockmvc.perform(post("/student/")).andExpect(status().isBadRequest());
	}

	@Test
	void testUpdateStudent() throws Exception {
		InsertStudentDto stud=new InsertStudentDto();
		
		stud.setId((long)1);
		stud.setInstitutionid((long) 1);
		stud.setRedgno((long) 310816);
		stud.setFname("ABC");
		stud.setLname("DEF");
		String str = "1998-03-31";
		Date date = Date.valueOf(str);
		stud.setDob(date);
		stud.setYear(4);
		stud.setMobileno((long) 994016369);
		stud.setEmail("abc@gmail.com");
		doNothing().when(studentService).updateStudent(stud);
		String orgJson = om.writeValueAsString(stud);
		MvcResult result = this.mockmvc
				.perform(put("/student/").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andExpect(status().isOk()).andReturn();
	}
	@Test
	void testUpdateExpectFailure() throws Exception {
		InsertStudentDto stud=new InsertStudentDto();
		doThrow(BadResponse.class).when(studentService).updateStudent(stud);
		this.mockmvc.perform(put("/student/")).andExpect(status().isBadRequest());
	}
	@Test
	void testDeleteStudent() throws Exception {
		student stud = new student();
		id = 1;
		when(studentService.getStudentById((long)id)).thenReturn(stud);
		this.mockmvc.perform(delete("/student/{id}", 1)).andExpect(status().isOk());
	}
	@Test
	void testDeleteExpectFailure() throws Exception {
		id = 1;
		doThrow(NotFound.class).when(studentService).deleteStudent((long)id);
		this.mockmvc.perform(delete("/student/{id}", 1)).andExpect(status().isNotFound());
	}

}
