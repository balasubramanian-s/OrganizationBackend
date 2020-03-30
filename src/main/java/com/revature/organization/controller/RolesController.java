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
import com.revature.organization.model.Roles;
import com.revature.organization.service.RolesService;

@RestController
@RequestMapping("/role")
@CrossOrigin(origins = "*")
public class RolesController {
	@Autowired
	private RolesService rolesService;

	// CONTROLLER FOR ROLES
		@GetMapping("/roles")
		public List<Roles> getRoles() throws ServiceException {
			return rolesService.get();
		}

		@GetMapping("/roles/{id}")
		public Roles getRolesById(@PathVariable Long id) throws ServiceException {
			return rolesService.get(id);

		}

		@PostMapping("/roles")
		public Roles save(@RequestBody Roles role) throws DBException {
			rolesService.save(role);
			return role;
		}

		@PutMapping("/roles")
		public Roles update(@RequestBody Roles role) throws DBException {
			rolesService.save(role);
			return role;

		}

		@DeleteMapping("/roles/{id}")
		public String deleteRole(@PathVariable Long id) throws ServiceException {
			rolesService.delete(id);
			return "Role Deleted with id:" + id;
		}


}
