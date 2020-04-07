package com.revature.organization.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.model.Faculty;
import com.revature.organization.service.FacultyService;
import com.revature.organization.util.FacultyMessage;
import com.revature.organization.util.OrganizationMessage;




@RestController
@RequestMapping("/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
	@Autowired
	private FacultyService facultyService;
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/")
	public ResponseEntity<HttpStatusResponse> getAllFaculty() throws ServiceException {
		try {
			List<Faculty> list =facultyService.getAllFaculty();
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		}catch(NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/institution/{inst_id}")
	public ResponseEntity<HttpStatusResponse> getbyInst(@PathVariable Long inst_id) throws ServiceException {

		try {
			List<Faculty> list =facultyService.getByFacultyInstitution(inst_id);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		}catch(NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<HttpStatusResponse> get(@PathVariable Long id) throws ServiceException {

		try {
			Faculty faculty =facultyService.getFaculty(id);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", faculty), HttpStatus.OK);
		}catch(NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
	@PostMapping("/")
	public ResponseEntity<HttpStatusResponse> save(@Valid @RequestBody InsertFacultyDto dto) throws DBException {
		try {
			facultyService.saveFaculty(dto);
			return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.CREATED.value(), "Data Insert Success", dto),HttpStatus.CREATED);
			
		}catch(BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
			
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
	@PutMapping("/")
	public ResponseEntity<HttpStatusResponse> update(@Valid @RequestBody InsertFacultyDto dto) throws DBException {
		try {
			facultyService.updateFaculty(dto);
			return new ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(), "Data Updated", dto),HttpStatus.OK);
			
		}catch(BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Updated",null),HttpStatus.BAD_REQUEST);
			
		}catch(NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);

		}
		
		
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatusResponse> delete(@PathVariable Long id) throws NotFound {
		try {
			facultyService.deleteFaculty(id);
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(), "Record Deleted ",null),HttpStatus.OK);
		}catch(NotFound e) {
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to make Changes",null),HttpStatus.NOT_FOUND);
		}

	}

	
}
