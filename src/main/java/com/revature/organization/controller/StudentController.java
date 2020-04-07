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
import com.revature.organization.dto.InsertStudentDto;

import com.revature.organization.exception.BadResponse;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.HttpStatusResponse;
import com.revature.organization.exception.NotFound;

import com.revature.organization.model.student;
import com.revature.organization.service.studentservice;

import com.revature.organization.util.StudentMessage;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
	@Autowired
	private studentservice studservice;
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/")
	public ResponseEntity<HttpStatusResponse> getAllStudent() throws NotFound {

		try {

			List<student> list = studservice.getAllStudent();
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/{id}")
	public ResponseEntity<HttpStatusResponse> getStudentById(@NotNull @PathVariable Long id) throws NotFound {

		try {

			student list = studservice.getStudentById(id);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/institution/{inst_id}")
	public ResponseEntity<HttpStatusResponse> getStudentByInst(@NotNull @PathVariable Long inst_id) throws NotFound {

		try {

			List<student> list = studservice.getStudbyInst(inst_id);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/institution/year/{institutionid}/{year}")
	public ResponseEntity<HttpStatusResponse> getStudentByInstYear(@NotNull @PathVariable Long institutionid, @PathVariable int year)
			throws NotFound {

		try {

			List<student> list = studservice.getStudbyInstYear(institutionid, year);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@GetMapping("/year/{year}")
	public ResponseEntity<HttpStatusResponse> getStudentByYear(@NotNull @PathVariable int year) throws NotFound, DBException {
		try {

			List<student> list = studservice.getStudbyYear(year);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Retrived", list), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}
	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@PostMapping("/")
	public ResponseEntity<HttpStatusResponse> saveStudent(@Valid @RequestBody InsertStudentDto dto) throws DBException, BadResponse {
		try {
			studservice.saveStudent(dto);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.CREATED.value(),"Data Inserted", null), HttpStatus.CREATED);
		} catch (BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
		}

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@PutMapping("/")
	public ResponseEntity<HttpStatusResponse> updateStudent(@Valid @RequestBody InsertStudentDto dto)
			throws DBException, BadResponse, NotFound {
		try {
			studservice.updateStudent(dto);
			return new  ResponseEntity<HttpStatusResponse>(new HttpStatusResponse(HttpStatus.OK.value(),"Data Updated", null), HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		} catch (BadResponse e) {
			return  new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(), "No Data Inserted",null),HttpStatus.BAD_REQUEST);
		} 

	}
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY','USER')")
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatusResponse> deleteStudent(@Valid @PathVariable Long id) throws NotFound {
		try {
			studservice.deleteStudent(id);
			return new ResponseEntity<HttpStatusResponse>( new HttpStatusResponse(HttpStatus.OK.value(), "Record Deleted ",null),HttpStatus.OK);
		} catch (NotFound e) {
			 return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NOT_FOUND.value(), "Unable to get records!!DB Empty", null),	HttpStatus.NOT_FOUND);
		}

	}

}
