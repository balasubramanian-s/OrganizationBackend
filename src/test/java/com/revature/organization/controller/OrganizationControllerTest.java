package com.revature.organization.controller;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;



import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.organization.exception.NotFound;
import com.revature.organization.model.Organization;
import com.revature.organization.service.OrganizationService;

@AutoConfigureRestDocs()
@WebAppConfiguration
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })

class OrganizationControllerTest extends AbstractSecurityTest {
	
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
	void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockmvc = MockMvcBuilders.standaloneSetup(organizationcontroller).apply(documentationConfiguration(restDocumentation)).build();
		
		login("admin", "pass");
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
		this.mockmvc.perform(get("/core/organization").contentType("application/json"))//.header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU4NzA3MDAwMiwiaWF0IjoxNTg3MDM0MDAyfQ.Z99johL9qTpftC0FMosWJbS8L-0GZRERk92tMYKbd9Y"))
		.andDo(print()).andExpect(status().isOk())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
	}

	@Test
	void testGetExpectFailure() throws Exception {
		doThrow(NotFound.class).when(organizationService).get();
		this.mockmvc.perform(get("/core/organization")).andExpect(status().isNoContent());
	}

	@Test
	void testGetActiveOrganization() throws Exception {
		
		when(organizationService.getActiveOrganization()).thenReturn(orgList);
		this.mockmvc.perform(get("/core/organization/active").contentType("application/json"))
		.andDo(print())
		.andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
		
	}

	@Test
	void testGetActiveExpectFailure() throws Exception {
		doThrow(NotFound.class).when(organizationService).getActiveOrganization();
		this.mockmvc.perform(get("/core/organization/active")).andExpect(status().isNoContent());
	}

	@Test
	void testGetOrganizationbyId() throws Exception {
		Organization organization = new Organization();
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		when(organizationService.get(id)).thenReturn(organization);
		this.mockmvc.perform(get("/core/organization/{id}", 1).contentType("application/json")).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

		
	}

	@Test
	void testSaveOrganization() throws Exception {
		Organization organization = new Organization();
		organization.setId((long) 1);
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		doNothing().when(organizationService).save(organization);
		String orgJson = om.writeValueAsString(organization);
		 this.mockmvc
				.perform(post("/core/organization").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson)).andDo(print())
				.andExpect(status().isCreated())
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;

	}

	@Test
	void testUpdateOrganization() throws Exception {
		Organization organization = new Organization();
		organization.setId((long) 1);
		organization.setName("KCG College of Engineering");
		organization.setAlias("KCG");
		organization.setUniversity("Anna University");
		organization.setIsActive(true);
		doNothing().when(organizationService).save(organization);
		String orgJson = om.writeValueAsString(organization);
		 this.mockmvc
				.perform(put("/core/organization").contentType(MediaType.APPLICATION_JSON_VALUE).content(orgJson)).andDo(print())
				.andExpect(status().isOk())	
				.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;
	}

	@Test
	void testDeleteOrganization() throws Exception {
		Organization organization = new Organization();
		id = (long) 1;
		when(organizationService.get(id)).thenReturn(organization);
		this.mockmvc.perform(delete("/core/organization/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));


	}

	@Test
	void testChangeStatusOrganization() throws Exception {
		id = (long) 1;
		doNothing().when(organizationService).changeStatus(id);
		this.mockmvc.perform(put("/core/organization/status/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
;

	}

}
