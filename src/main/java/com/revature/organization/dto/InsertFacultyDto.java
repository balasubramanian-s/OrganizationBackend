package com.revature.organization.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InsertFacultyDto {

	
	private Long id;
	
	private int employee_id;

	private Long institution_id;

	private String first_name;

	private String last_name;

	private Date dob;

	private String email;

	private Long mobile_no;

	private Long role_id;
	@JsonIgnore
	private LocalDateTime createdon=LocalDateTime.now();
	@JsonIgnore
	private LocalDateTime modifiedon=LocalDateTime.now();
	

	
	



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public int getEmployee_id() {
		return employee_id;
	}



	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}



	public Long getInstitution_id() {
		return institution_id;
	}



	public void setInstitution_id(Long institution_id) {
		this.institution_id = institution_id;
	}



	public String getFirst_name() {
		return first_name;
	}



	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}



	public String getLast_name() {
		return last_name;
	}



	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}



	public Date getDob() {
		return dob;
	}



	public void setDob(Date dob) {
		this.dob = dob;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}






	public Long getMobile_no() {
		return mobile_no;
	}



	public void setMobile_no(Long mobile_no) {
		this.mobile_no = mobile_no;
	}



	public Long getRole_id() {
		return role_id;
	}



	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}



	public LocalDateTime getCreatedon() {
		return createdon;
	}



	public void setCreatedon(LocalDateTime createdon) {
		this.createdon = createdon;
	}



	public LocalDateTime getModifiedon() {
		return modifiedon;
	}



	public void setModifiedon(LocalDateTime modifiedon) {
		this.modifiedon = modifiedon;
	}



	@Override
	public String toString() {
		return "InsertFacultyDto [id=" + id + ", employee_id=" + employee_id + ", institution_id=" + institution_id
				+ ", first_name=" + first_name + ", last_name=" + last_name + ", dob=" + dob + ", email=" + email
				+ ", mobile_no=" + mobile_no + ", role_id=" + role_id + ", createdon=" + createdon + ", modifiedon="
				+ modifiedon + "]";
	}
	

	
	
	

}
