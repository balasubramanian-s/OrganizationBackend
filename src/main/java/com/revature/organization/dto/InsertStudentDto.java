package com.revature.organization.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class InsertStudentDto {
	private Long id;
	private Long institutionid;
	private Long redgno;
	private String fname;
	private String lname;
	private Date dob;
	private Integer year;
	private Long mobileno;
	private String email;
	
	@JsonIgnore
	private LocalDateTime createdon=LocalDateTime.now();
	@JsonIgnore
	private LocalDateTime modifiedon=LocalDateTime.now();

	public Long getInstitutionid() {
		return institutionid;
	}
	public void setInstitutionid(Long institutionid) {
		this.institutionid = institutionid;
	}
	public Long getRedgno() {
		return redgno;
	}
	public void setRedgno(Long redgno) {
		this.redgno = redgno;
	}
	public Long getMobileno() {
		return mobileno;
	}
	public void setMobileno(Long mobileno) {
		this.mobileno = mobileno;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		return "InsertDTO [id=" + id + ", institutionid=" + institutionid + ", redgno=" + redgno + ", fname=" + fname
				+ ", lname=" + lname + ", dob=" + dob + ", year=" + year + ", mobileno=" + mobileno + ", email=" + email
				+ ", createdon=" + createdon + ", modifiedon=" + modifiedon + "]";
	}
	
	

}
