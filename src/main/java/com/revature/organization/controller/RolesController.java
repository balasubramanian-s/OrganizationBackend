package com.revature.organization.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
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
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
		@GetMapping("/")
		public List<Roles> getRoles() throws ServiceException, NotFound {
			return rolesService.get();
		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
		@GetMapping("/{id}")
		public Roles getRolesById(@NotNull @PathVariable Long id) throws  NotFound {
			
				return rolesService.get(id);
			

		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@PostMapping("/")
		public Roles save(@Valid @RequestBody Roles role) throws DBException, BadResponse {
			rolesService.save(role);
			return role;
		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@PutMapping("/")
		public Roles update(@Valid @RequestBody Roles role) throws DBException, BadResponse {
			rolesService.save(role);
			return role;

		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@DeleteMapping("/{id}")
		public String deleteRole(@NotNull @PathVariable Long id) throws  NotFound {
			rolesService.delete(id);
			return "Role Deleted with id:" + id;
		}


}
