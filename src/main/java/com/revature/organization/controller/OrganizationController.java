
package com.revature.organization.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.revature.organization.dto.ResponseEntity;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
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

	@GetMapping("/organization")
	public ResponseEntity get() throws ServiceException, NotFound {
		try {
			List<Organization> list=organizationService.get();
			
			return new ResponseEntity(HttpStatus.OK.value(),"Data Retrived",list);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORDS);
		}		

	}
	@GetMapping("/organization/active")
	public ResponseEntity getActiveOrganization() throws ServiceException, NotFound {
		try {
			List<Organization> list=organizationService.getActiveOrganization();
			
			return new ResponseEntity(HttpStatus.OK.value(),"Data Retrived",list);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORDS);
		}		

	}
	
	@ResponseStatus
	@GetMapping("/organization/{id}")
	public ResponseEntity get(@PathVariable Long id) throws ServiceException, NotFound {
		
		try {
			Organization Obj = organizationService.get(id);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Found",Obj);
			
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NO_CONTENT.value(),OrganizationMessage.NO_RECORD);
			
		}
		
	}

	@PostMapping("/organization")
	public ResponseEntity save(@Valid @RequestBody Organization Obj) throws MethodArgumentNotValidException, BadResponse, DBException {
		try {
			organizationService.save(Obj);
			return new ResponseEntity(HttpStatus.CREATED.value(), "Data Insert Success", Obj);
			
		}catch(BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), "No Data Inserted");
			
		}
					
		
	}
	

	

	@PutMapping("/organization")
	public ResponseEntity update(@Valid @RequestBody Organization organizationObj) throws DBException,BadResponse,NotFound{
		try {
			organizationService.update(organizationObj);
			return new ResponseEntity(HttpStatus.CREATED.value(), "Data Updated", organizationObj);
			
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), OrganizationMessage.NO_RECORD);
		}
		catch(BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), "Update Failed");
			
		}
	}
	
	@DeleteMapping("/organization/{id}")
	public ResponseEntity delete(@PathVariable Long id) throws ServiceException, NotFound {
		
		try {
			organizationService.delete(id);
			return new ResponseEntity(HttpStatus.OK.value(), "Record Deleted with id:"+id,null);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), OrganizationMessage.NO_RECORD);
		}
		
		
	}
	
	@PutMapping("/organizations/status/{id}")
	public ResponseEntity changeStatus(@PathVariable Long id) throws DBException,NotFound {
		try {
			organizationService.changeStatus(id);
			return new ResponseEntity(HttpStatus.OK.value(), "Status Changed");
		}
		catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_MODIFIED.value(), "Unable to Change Status");
		}
		

	}

}
