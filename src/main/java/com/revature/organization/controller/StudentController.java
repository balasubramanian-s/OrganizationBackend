package com.revature.organization.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.dto.ResponseEntity;
import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.NotFound;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.student;
import com.revature.organization.service.studentservice;
import com.revature.organization.util.OrganizationMessage;
import com.revature.organization.util.StudentMessage;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
	@Autowired
	private studentservice studservice;
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/")
	public ResponseEntity getAllStudent() throws NotFound {

		try {

			List<student> list = studservice.getAllStudent();
			return new ResponseEntity(HttpStatus.OK.value(), "Data Retrived", list);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/{id}")
	public ResponseEntity getStudentById(@NotNull @PathVariable Long id) throws NotFound {

		try {

			student list = studservice.getStudentById(id);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Retrived", list);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/institution/{inst_id}")
	public ResponseEntity getStudentByInst(@NotNull @PathVariable Long inst_id) throws NotFound {

		try {

			List<student> list = studservice.getStudbyInst(inst_id);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Retrived", list);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/institution/year/{institutionid}/{year}")
	public ResponseEntity getStudentByInstYear(@NotNull @PathVariable Long institutionid, @PathVariable int year)
			throws NotFound {

		try {

			List<student> list = studservice.getStudbyInstYear(institutionid, year);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Retrived", list);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/year/{year}")
	public ResponseEntity getStudentByYear(@NotNull @PathVariable int year) throws NotFound, DBException {
		try {

			List<student> list = studservice.getStudbyYear(year);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Retrived", list);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@PostMapping("/")
	public ResponseEntity saveStudent(@Valid @RequestBody InsertStudentDto dto) throws DBException, BadResponse {
		try {
			studservice.saveStudent(dto);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Inserted", dto);
		} catch (BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), StudentMessage.UNABLE_TO_INSERT);
		} catch (DBException e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong");
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@PutMapping("/")
	public ResponseEntity updateStudent(@Valid @RequestBody InsertStudentDto dto)
			throws DBException, BadResponse, NotFound {
		try {
			studservice.updateStudent(dto);
			return new ResponseEntity(HttpStatus.OK.value(), "Data Updated", dto);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		} catch (BadResponse e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST.value(), StudentMessage.UNABLE_TO_INSERT);
		} catch (DBException e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something Went Wrong");
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity deleteStudent(@Valid @PathVariable Long id) throws NotFound {
		try {
			studservice.deleteStudent(id);
			return new ResponseEntity(HttpStatus.ACCEPTED.value(), "Student of " + id + "deleted", id);
		} catch (NotFound e) {
			return new ResponseEntity(HttpStatus.NOT_FOUND.value(), StudentMessage.UNABLE_TO_FIND);
		}

	}

}
