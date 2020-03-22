

package com.revature.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Organization save(@RequestBody Organization organizationObj) throws DBException {
		organizationService.save(organizationObj);
		return organizationObj;

	}

	@GetMapping("/organizations/{id}")
	public Organization get(@PathVariable Long id) throws ServiceException {
		Organization organizationObj= organizationService.get(id);
		
		return organizationObj;
	}
	
	@DeleteMapping("/organizations/{id}")
	public String delete(@PathVariable Long id) throws ServiceException {
		organizationService.delete(id);
		return "Employee Deleted with id:"+id;
	}

	@PutMapping("/organizations")
	public Organization update(@RequestBody Organization organizationObj) throws DBException {
		organizationService.save(organizationObj);
		return organizationObj;
	}
	@PutMapping("/organizations/status/{id}")
	public  void changeStatus(@PathVariable Long id) throws DBException {
		organizationService.changeStatus(id);
		
	}
	
	
	
}
