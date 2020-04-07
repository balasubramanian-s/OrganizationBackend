package com.revature.organization.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.revature.organization.exception.HttpStatusResponse;
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
		public ResponseEntity<HttpStatusResponse> getRoles() throws  NotFound {
			try {
			List<Roles> list= rolesService.get();
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);

			}catch(NotFound e) {
				 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);

			}
			
		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
		@GetMapping("/{id}")
		public  ResponseEntity<HttpStatusResponse> getRolesById(@NotNull @PathVariable Long id) throws  NotFound {
			try {
				Roles role= rolesService.get(id);
				return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", role), HttpStatus.OK);

			}catch(NotFound e) {
				 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);

			}
				
			

		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@PostMapping("/")
		public ResponseEntity<HttpStatusResponse> save(@Valid @RequestBody Roles role) throws DBException, BadResponse {
		try {
			rolesService.save(role);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.CREATED.value(),"Data Inserted", null), HttpStatus.CREATED);

		}catch (BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
		}
			
		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@PutMapping("/")
		public ResponseEntity<HttpStatusResponse> update(@Valid @RequestBody Roles role) throws DBException, BadResponse {
			try {
				rolesService.save(role);
				return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.CREATED.value(),"Data Inserted", null), HttpStatus.CREATED);

			}catch (BadResponse e) {
				return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
			}
		}
		@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
		@DeleteMapping("/{id}")
		public ResponseEntity<HttpStatusResponse> deleteRole(@NotNull @PathVariable Long id) throws  NotFound {
			try {
				rolesService.delete(id);
				return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", null), HttpStatus.OK);

			}catch(NotFound e) {
				 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);

			}
			
			
		}


}
