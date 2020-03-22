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
import com.revature.organization.dto.InsertFacultyDto;
import com.revature.organization.model.Faculty;
import com.revature.organization.service.FacultyService;


@RestController
@RequestMapping("/core")
@CrossOrigin(origins = "*")
public class FacultyController {
	@Autowired
	private FacultyService facultyService;
	
	@GetMapping("/faculty")
	public List<Faculty> get() throws ServiceException {
		return facultyService.get();

	}

	@GetMapping("/faculty/institution/{inst_id}")
	public List<Faculty> getbyInst(@PathVariable Long inst_id) throws ServiceException {

		return facultyService.getByInstitution(inst_id);
	}

	@GetMapping("/faculty/{id}")
	public Faculty get(@PathVariable Long id) throws ServiceException {

		Faculty facObj = facultyService.get(id);

		return facObj;
	}

	@PostMapping("/faculty")
	public InsertFacultyDto save(@RequestBody InsertFacultyDto fac) throws DBException {
		facultyService.save(fac);
		return fac;
	}

	@PutMapping("/faculty")
	public InsertFacultyDto update(@RequestBody InsertFacultyDto fac) throws DBException {
		facultyService.save(fac);
		return fac;
	}

	@DeleteMapping("/faculty/{id}")
	public void delete(@PathVariable Long id) throws ServiceException {
		facultyService.delete(id);
		System.out.println("Faculty Deleted With id:" + id);

	}

	
}
