package com.revature.organization.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Faculty;
import com.revature.organization.model.Organization;
import com.revature.organization.model.Roles;
import com.revature.organization.service.FacultyService;
@ExtendWith({RestDocumentationExtension.class,SpringExtension.class})
@WebAppConfiguration
@AutoConfigureRestDocs()
class FacultyControllerTest extends AbstractSecurityTest {
	
	

	
	private MockMvc mockmvc;

	private ObjectMapper om = new ObjectMapper();

	@InjectMocks
	FacultyController facultycontroller;

	@Mock
	FacultyService facultyService;

	@Spy
	List<Faculty> facList = new ArrayList<Faculty>();

	private Organization org;

	private Roles roles;

	private long id;

	@BeforeEach
	void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockmvc = MockMvcBuilders.standaloneSetup(facultycontroller).apply(documentationConfiguration(restDocumentation)).build();
				
		login("admin", "pass");
		facList = getFacList();
	}
	private List<Faculty> getFacList() {
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
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);

		facList.add(faculty);
		return facList;
	}

	@Test
	void testGetAllFaculty() throws  Exception  {

		  
	when(facultyService.getAllFaculty()).thenReturn(facList);
		this.mockmvc.perform(get("/faculty/")).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}
	@Test
	void testGetAllFacultyExpectFailure() throws Exception {
		doThrow(NotFound.class).when(facultyService).getAllFaculty();
		this.mockmvc.perform(get("/faculty/")).andExpect(status().isNoContent());
	}
	@Test
	void testGetbyInst() throws Exception {
		when(facultyService.getByFacultyInstitution(id)).thenReturn(facList);
		this.mockmvc.perform(get("/faculty/institution/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));


	}
	@Test
	void testGetbyInstExpectFailure() throws Exception {
		id = (long) 1;
		doThrow(NotFound.class).when(facultyService).getByFacultyInstitution(id);
		this.mockmvc.perform(get("/faculty/institution/{id}", 1)).andExpect(status().isNoContent());
	}

	@Test
	void testGetFaculty() throws Exception {
		Faculty faculty = new Faculty();
		faculty.setEmployee_id(1);
		faculty.setFirst_name("abc");
		faculty.setLast_name("def");
		faculty.setOrg(org);
		faculty.setRoles(roles);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		faculty.setDob(date);
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);
		when(facultyService.getFaculty(id)).thenReturn(faculty);
		this.mockmvc.perform(get("/faculty/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	
	}
	@Test
	void testGetExpectFailure() throws Exception {
		id = (long) 1;
		doThrow(NotFound.class).when(facultyService).getFaculty(id);
		this.mockmvc.perform(get("/faculty/{id}", 1)).andExpect(status().isNotFound());
	}

	@Test
	void testSaveFaculty() throws Exception {
		InsertFacultyDto faculty = new InsertFacultyDto();
		faculty.setId((long) 1);
		faculty.setEmployee_id(1);
		faculty.setFirst_name("abc");
		faculty.setLast_name("def");
		faculty.setInstitution_id((long) 1);
		faculty.setRole_id((long) 3);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		faculty.setDob(date);
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);
		doNothing().when(facultyService).saveFaculty(faculty);
		String orgJson = om.writeValueAsString(faculty);
		 this.mockmvc
				.perform(post("/faculty/").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andDo(print()).andExpect(status().isCreated())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));


	}
	@Test
	void testSaveExpectFailure() throws Exception {
		InsertFacultyDto faculty = new InsertFacultyDto();
		doThrow(BadResponse.class).when(facultyService).saveFaculty(faculty);
		this.mockmvc.perform(post("/faculty/")).andExpect(status().isBadRequest());
	}
	@Test
	void testUpdateFaculty() throws Exception {
		InsertFacultyDto faculty = new InsertFacultyDto();
		faculty.setId((long) 1);
		faculty.setEmployee_id(1);
		faculty.setFirst_name("abc");
		faculty.setLast_name("def");
		faculty.setInstitution_id((long) 1);
		faculty.setRole_id((long) 3);
		String str = "1985-03-31";
		Date date = Date.valueOf(str);
		faculty.setDob(date);
		faculty.setEmail("abc@gmail.com");
		faculty.setMobile_no((long) 998451233);
		doNothing().when(facultyService).updateFaculty(faculty);
		String orgJson = om.writeValueAsString(faculty);
		this.mockmvc
				.perform(put("/faculty/").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andDo(print()).andExpect(status().isOk())	
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));


	}
	@Test
	void testUpdateExpectFailureBadRequest() throws Exception {
		InsertFacultyDto faculty = new InsertFacultyDto();
		doThrow(BadResponse.class).when(facultyService).updateFaculty(faculty);
		this.mockmvc.perform(post("/faculty/")).andExpect(status().isBadRequest());
	}

	
	
	
	
	@Test
	void testDeleteFaculty() throws Exception {
		Faculty faculty = new Faculty();
		id = (long) 1;
		when(facultyService.getFaculty(id)).thenReturn(faculty);
		this.mockmvc.perform(delete("/faculty/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}
	@Test
	void testDeleteExpectFailure() throws Exception {
		id = (long) 1;
		doThrow(NotFound.class).when(facultyService).deleteFaculty(id);
		this.mockmvc.perform(delete("/faculty/{id}", 1)).andExpect(status().isNotFound());
	}
}
