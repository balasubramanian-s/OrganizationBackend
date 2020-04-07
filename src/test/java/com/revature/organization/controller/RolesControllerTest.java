package com.revature.organization.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Roles;
import com.revature.organization.service.RolesService;

class RolesControllerTest {

	private MockMvc mockmvc;

	private Long id;

	private ObjectMapper om = new ObjectMapper();

	@InjectMocks
	RolesController rolescontroller;

	@Mock
	RolesService rolesService;

	@Spy
	List<Roles> roleList = new ArrayList<Roles>();

	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(rolescontroller).build();
		roleList = getRoleList();
	}

	private List<Roles> getRoleList() {
		Roles role = new Roles();
		role.setId((long) 1);
		role.setName("HOD");
		roleList.add(role);
		return roleList;
	}

	@Test
	void testGetRoles() throws Exception {
		when(rolesService.get()).thenReturn(roleList);
		this.mockmvc.perform(get("/role/")).andExpect(status().isOk());
	}

	

	@Test
	void testGetRolesById() throws Exception {
		Roles role = new Roles();
		role.setName("HOD");
		when(rolesService.get(id)).thenReturn(role);
		this.mockmvc.perform(get("/role/{id}", 1)).andExpect(status().isOk());
	}



	@Test
	void testSave() throws Exception {
		Roles role = new Roles();
		role.setId((long) 1);
		role.setName("HOD");
		doNothing().when(rolesService).save(role);
		String rolesJson = om.writeValueAsString(role);
		MvcResult result = this.mockmvc
				.perform(post("/role/").contentType(MediaType.APPLICATION_JSON_VALUE).content(rolesJson))
				.andExpect(status().isCreated()).andReturn();
	}


	@Test
	void testDeleteRole() throws Exception {
		Roles role = new Roles();
		id = (long) 1;
		when(rolesService.get(id)).thenReturn(role);
		this.mockmvc.perform(delete("/role/{id}", 1)).andExpect(status().isOk());
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
		doThrow(NotFound.class).when(rolesService).get();
		this.mockmvc.perform(get("/role/")).andExpect(status().isNotFound());
	}

}