package com.revature.organization.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.dto.ResponseEntity;
import com.revature.organization.model.Faculty;
import com.revature.organization.service.FacultyService;
import com.revature.organization.util.FacultyMessage;
import com.revature.organization.util.OrganizationMessage;

import javassist.NotFoundException;


@RestController
@RequestMapping("/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/")
	public ResponseEntity getAllFaculty() throws ServiceException {
		try {
			List<Faculty> list =facultyService.getAllFaculty();
			return new ResponseEntity(HttpStatus.OK.value(),"Data Retrived",list);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORDS);
		}

	}

	@GetMapping("/institution/{inst_id}")
	public ResponseEntity getbyInst(@PathVariable Long inst_id) throws ServiceException {

		try {
			List<Faculty> list =facultyService.getByFacultyInstitution(inst_id);
			return new ResponseEntity(HttpStatus.OK.value(),"Data Retrived",list);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORDS);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable Long id) throws ServiceException {

		try {
			Faculty faculty =facultyService.getFaculty(id);
			return new ResponseEntity(HttpStatus.OK.value(),"Data Retrived",faculty);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(),OrganizationMessage.NO_RECORDS);
		}
	}

	@PostMapping("/")
	public ResponseEntity save(@Valid @RequestBody InsertFacultyDto dto) throws DBException {
		try {
			facultyService.saveFaculty(dto);
			return new ResponseEntity(HttpStatus.CREATED.value(), "Inserted  Successfullty", dto);
			
		}catch(BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), "Insertion Failed");
			
		}
	}

	@PutMapping("/")
	public ResponseEntity update(@Valid @RequestBody InsertFacultyDto dto) throws DBException, NotFound {
		try {
			facultyService.updateFaculty(dto);
			return new ResponseEntity(HttpStatus.CREATED.value(), "Updated Successfullty", dto);
			
		}catch(BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), "Update Failed");
			
		}
		
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) throws NotFound {
		try {
			facultyService.deleteFaculty(id);
			return new ResponseEntity(HttpStatus.OK.value(), "faculty Deleted with id:"+id,null);
		}catch(NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), FacultyMessage.UNABLE_TO_DELETE);
		}

	}

	
}
