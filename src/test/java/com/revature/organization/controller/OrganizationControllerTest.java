package com.revature.organization.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Organization;
import com.revature.organization.service.OrganizationService;

class OrganizationControllerTest {

	private MockMvc mockmvc;

	private ObjectMapper om = new ObjectMapper();

	@InjectMocks
	OrganizationController organizationcontroller;

	@Mock
	OrganizationService organizationService;

	@Spy
	List<Organization> orgList = new ArrayList<Organization>();

	private Long id;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockmvc = MockMvcBuilders.standaloneSetup(organizationcontroller).build();
		orgList = getOrgList();
	}
	private List<Organization> getOrgList() {
		Organization organization = new Organization();
		organization.setId((long) 1);
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);

		orgList.add(organization);
		return orgList;
	}


	@Test
	void testGetAllOrganization() throws Exception {
		when(organizationService.get()).thenReturn(orgList);
		this.mockmvc.perform(get("/core/organization")).andExpect(status().isOk());
	}
	@Test
	void testGetExpectFailure() throws Exception {
		doThrow(NotFound.class).when(organizationService).get();
		this.mockmvc.perform(get("/core/organization")).andExpect(status().isNotFound());
	}
	@Test
	void testGetActiveOrganization() throws Exception {
		when(organizationService.getActiveOrganization()).thenReturn(orgList);
		this.mockmvc.perform(get("/core/organization/active")).andExpect(status().isOk());
	}
	@Test
	void testGetActiveExpectFailure() throws Exception {
		doThrow(NotFound.class).when(organizationService).getActiveOrganization();
		this.mockmvc.perform(get("/core/organization/active")).andExpect(status().isNotFound());
	}

	@Test
	void testGet() throws Exception {
		Organization organization = new Organization();
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		when(organizationService.get(id)).thenReturn(organization);
		this.mockmvc.perform(get("/core/organization/{id}", 1)).andExpect(status().isOk());
	}

	@Test
	void testSave() throws Exception {
		Organization organization = new Organization();
		organization.setId((long) 1);
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		doNothing().when(organizationService).save(organization);
		String orgJson = om.writeValueAsString(organization);
		MvcResult result = this.mockmvc
				.perform(post("/core/organization").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andExpect(status().isCreated()).andReturn();

	}

	@Test
	void testUpdate() throws Exception {
		Organization organization = new Organization();
		organization.setId((long) 1);
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		doNothing().when(organizationService).save(organization);
		String orgJson = om.writeValueAsString(organization);
		MvcResult result = this.mockmvc
				.perform(put("/core/organization").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	void testDelete() throws Exception {
		Organization organization = new Organization();
		id = (long) 1;
		when(organizationService.get(id)).thenReturn(organization);
		this.mockmvc.perform(delete("/core/organization/{id}", 1)).andExpect(status().isOk());

	}

	@Test
	void testChangeStatus() throws Exception {
		Organization organization = new Organization();
		id = (long) 1;
		doNothing().when(organizationService).changeStatus(id);
		this.mockmvc.perform(put("/core/organization/status/{id}", 1)).andExpect(status().isOk());

	}

}
