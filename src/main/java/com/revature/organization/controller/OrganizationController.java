
package com.revature.organization.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.dto.ResponseEntity;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;

import com.revature.organization.model.Organization;
import com.revature.organization.service.OrganizationService;


@RestController
@RequestMapping("/core")
@CrossOrigin(origins = "*")

public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@GetMapping("/organizations")
	public List<Organization> get() throws ServiceException {

		return organizationService.get();

	}

	@PostMapping("/organizations")
	public ResponseEntity save(@Valid @RequestBody Organization organizationObj) throws DBException {
		
		
	
		Organization org=	organizationService.save(organizationObj);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Insert Success", organizationObj);
		
		
		
	}

	@GetMapping("/organizations/{id}")
	public Organization get(@PathVariable Long id) throws ServiceException {
		Organization organizationObj = organizationService.get(id);

		return organizationObj;
	}

	@DeleteMapping("/organizations/{id}")
	public String delete(@PathVariable Long id) throws ServiceException {
		organizationService.delete(id);
		return "Employee Deleted with id:" + id;
	}

	@PutMapping("/organizations")
	public ResponseEntity update(@RequestBody Organization organizationObj) throws DBException,MethodArgumentNotValidException {
		try {
			organizationService.save(organizationObj);
			return new ResponseEntity(HttpStatus.OK.value(), "Updated  Successfully", organizationObj);
		}catch(MethodArgumentNotValidException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), " One or More Fields Missing", e.getBindingResult().getFieldError());
			
		}
	}

	@PutMapping("/organizations/status/{id}")
	public void changeStatus(@PathVariable Long id) throws DBException {
		organizationService.changeStatus(id);

	}

}
