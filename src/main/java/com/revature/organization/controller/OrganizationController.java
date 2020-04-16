
package com.revature.organization.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.HttpStatusResponse;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.Organization;
import com.revature.organization.service.OrganizationService;
import com.revature.organization.util.OrganizationMessage;


@RestController
@RequestMapping("/core")
@CrossOrigin(origins = "*")

public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	

	
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/organization")
	@ResponseBody
	public ResponseEntity<HttpStatusResponse> getAllOrganization() throws ServiceException, NotFound {
		try {
			List<Organization> list=organizationService.get();
			
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
	}catch(NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NO_CONTENT.value(), "No Data", null),	HttpStatus.NO_CONTENT);
		}		

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/organization/active")
	public ResponseEntity<HttpStatusResponse>  getActiveOrganization() throws ServiceException, NotFound {
		try {
			List<Organization> list=organizationService.getActiveOrganization();
			
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived",list),HttpStatus.OK);
		}catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NO_CONTENT.value(),"NotFound",null),HttpStatus.NO_CONTENT);
		}		

	}
	
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/organization/{id}")
	public ResponseEntity<HttpStatusResponse> get(@PathVariable Long id) throws ServiceException, NotFound {
		
		try {
			Organization Obj = organizationService.get(id);
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived",Obj),HttpStatus.OK);
			
		}catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NOT_FOUND.value(),"Not Found",null),HttpStatus.NOT_FOUND);
			
		}
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/organization")
	public ResponseEntity<HttpStatusResponse> save(@Valid @RequestBody Organization Obj) throws MethodArgumentNotValidException, BadResponse, DBException {
		try {
			organizationService.save(Obj);
			return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.CREATED.value(), "Data Insert Success", null),HttpStatus.CREATED);
			
		}catch(BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
			
		}
					
		
	}
	

	@PreAuthorize("hasRole('ADMIN')")

	@PutMapping("/organization")
	public ResponseEntity update(@Valid @RequestBody Organization Obj) throws DBException,BadResponse,NotFound{
		try {
			organizationService.update(Obj);
			return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(), "Data Updated", Obj),HttpStatus.OK);
			
		}catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NO_CONTENT.value(),"Not Found",null),HttpStatus.NO_CONTENT);
		}
		catch(BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Updated",null),HttpStatus.BAD_REQUEST);
			
		}
	}
	@PreAuthorize("hasRole('ADMIN')")	
	@DeleteMapping("/organization/{id}")
	public ResponseEntity delete(@PathVariable Long id) throws ServiceException, NotFound {
		
		try {
			organizationService.delete(id);
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(), "Record Deleted ",null),HttpStatus.OK);
		}catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), OrganizationMessage.NO_RECORD,null),HttpStatus.NOT_FOUND);
		}
		
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/organization/status/{id}")
	public ResponseEntity changeStatus(@PathVariable Long id ) throws DBException,NotFound {
		try {
			organizationService.changeStatus(id);
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(), "Status Changed ",null),HttpStatus.OK);
		}
		catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NO_CONTENT.value(), "Unable to make Changes",null),HttpStatus.NO_CONTENT);
		}
		

	}

}
