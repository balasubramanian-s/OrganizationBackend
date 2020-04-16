package com.revature.organization.controller;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Roles;
import com.revature.organization.service.RolesService;


@ExtendWith({RestDocumentationExtension.class,SpringExtension.class})
@WebAppConfiguration
@AutoConfigureRestDocs()

class RolesControllerTest extends AbstractSecurityTest {

	private MockMvc mockmvc;

	
	
	private Long id;
	
	private Integer pageNo;
	
	private Integer pageSize;

	private ObjectMapper om = new ObjectMapper();

	@InjectMocks
	RolesController rolescontroller;

	@Mock
	RolesService rolesService;

	@Spy
	List<Roles> roleList = new ArrayList<Roles>();

	@BeforeEach
	void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockmvc = MockMvcBuilders.standaloneSetup(rolescontroller).apply(documentationConfiguration(restDocumentation)).build();
			login("admin", "pass");
				
				roleList = getRoleList();
				
	}

	private List<Roles> getRoleList() {
		Roles role = new Roles();
		Roles role1 = new Roles();
		role.setId((long) 1);
		role.setName("HOD");
		role1.setId((long) 2);
		role1.setName("Professor");
		roleList.add(role);
		roleList.add(role1);
		return roleList;
	}

	@Test
	void testGetRoles() throws Exception {
		when(rolesService.getRoles(pageNo,pageSize)).thenReturn(roleList);
		this.mockmvc.perform(get("/role?pageSize=10&pageNo=0"))
		.andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;
	}

	

	@Test
	void testGetRolesById() throws Exception {
		Roles role = new Roles();
		role.setName("HOD");
		when(rolesService.get(id)).thenReturn(role);
		this.mockmvc.perform(get("/role/{id}", 1))
		.andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

	}



	@Test
	void testSave() throws Exception {
		Roles role = new Roles();
		role.setId((long) 1);
		role.setName("HOD");
		doNothing().when(rolesService).save(role);
		String rolesJson = om.writeValueAsString(role);
		this.mockmvc.perform(post("/role/").contentType(MediaType.APPLICATION_JSON_VALUE).content(rolesJson))
		.andDo(print())	.andExpect(status().isCreated())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;
	}


	@Test
	void testDeleteRole() throws Exception {
		Roles role = new Roles();
		id = (long) 1;
		when(rolesService.get(id)).thenReturn(role);
		this.mockmvc.perform(delete("/role/{id}", 1))
		.andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;
	}
	
	@Test
	void testGetRolesByIdExpectFailure() throws Exception {
		id = (long) 1;
		doThrow(NotFound.class).when(rolesService).get(id);
		this.mockmvc.perform(get("/role/{id}", 1)).andExpect(status().isNotFound());
	}
	
	@Test
	void testSaveExpectFailure() throws Exception {
		Roles role = new Roles();
		doThrow(BadResponse.class).when(rolesService).save(role);
		this.mockmvc.perform(post("/role/")).andExpect(status().isBadRequest());
	}
	
	@Test
	void testDeleteRoleExpectFailure() throws Exception {
		id = (long) 1;
		doThrow(NotFound.class).when(rolesService).delete(id);
		this.mockmvc.perform(delete("/role/{id}", 1)).andExpect(status().isNotFound());
	
	}
	@Test
	void testGetRolesExpectFailure() throws Exception {
		doThrow(NotFound.class).when(rolesService).getRoles(pageNo,pageSize);
		this.mockmvc.perform(get("/role?pageSize=&pageNo=")).andExpect(status().isNoContent());
	}

}