package com.revature.organization.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.organization.dto.InsertStudentDto;
import com.revature.organization.exception.DBException;
import com.revature.organization.exception.ServiceException;
import com.revature.organization.model.student;
import com.revature.organization.service.studentservice;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "*")
public class StudentController {
	@Autowired
	private studentservice studservice;

	@GetMapping("/getall")
	public List<student> get() throws ServiceException {
		return studservice.get();
	}

	@GetMapping("/get/{id}")
	public student get(@PathVariable Long id) throws ServiceException {
		return studservice.get(id);
	}

	@GetMapping("/institution/{inst_id}")
	public List<student> getstudbyInst(@PathVariable Long inst_id) throws ServiceException {

		return studservice.getstudbyInst(inst_id);
	}

	@GetMapping("/year/{institutionid}/{year}")
	public List<student> getstudbyInstYear(@PathVariable Long institutionid, @PathVariable int year)
			throws ServiceException {
		return studservice.getstudbyInstYear(institutionid, year);
	}

	@GetMapping("/year/{year}")
	public List<student> getstudbyYear(@PathVariable int year) throws ServiceException {
		return studservice.getstudbyYear(year);
	}

	@PostMapping("/addStudent")
	public void save(@RequestBody InsertStudentDto idto) throws DBException {
		studservice.save(idto);
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) throws ServiceException {
		studservice.delete(id);
		return "Student of "+id+" deleted";

	}

}
