package com.revature.organization.model;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "`Student`")
public class student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull(message="Register number cannot be Empty")
	@Column(name = "`register_number`")
	private Long redgno;
	@NotNull(message="Organization cannot be Empty")
	@ManyToOne
	@JoinColumn(name = "`institution_id`")
	private Organization org;
	
	
	@NotEmpty(message="Firstname Cannnot be Empty")
	@Column(name = "`first_name`")
	private String fname;
	@NotEmpty(message="Lastname Cannnot be Empty")
	@Column(name = "`last_name`")
	private String lname;
	@NotNull(message="DateofBirth cannot be Empty")
	@Column(name = "`dob`")
	private Date dob;
	@NotNull(message="Year cannot be Empty")
	@Column(name = "`year`")
	private Integer year;
	@NotNull(message="Mobile Number cannot be Empty")
	@Column(name = "`mobile_number`")
	private Long mobileno;
	@NotEmpty(message="Email Cannnot be Empty")
	@Column(name = "`email`")
	private String email;
	@NotNull(message="CreatedOn cannot be Empty")
	@Column(name = "`created_on`")
	private LocalDateTime createdon;
	
	@Column(name = "`modified_on`")
	private LocalDateTime modifiedon;
	
	@Column(name = "`created_by`")
	private String createdby;
	
	@Column(name = "`modified_by`")
	private String modifiedby;
	
	public student() {
		super();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}



	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Long getRedgno() {
		return redgno;
	}

	public void setRedgno(Long redgno) {
		this.redgno = redgno;
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

	public Long getMobileno() {
		return mobileno;
	}

	@Override
	public String toString() {
		return "student [id=" + id + ", redgno=" + redgno + ", org=" + org + ", fname=" + fname + ", lname=" + lname
				+ ", dob=" + dob + ", year=" + year + ", mobileno=" + mobileno + ", email=" + email + ", createdon="
				+ createdon + ", modifiedon=" + modifiedon + ", createdby=" + createdby + ", modifiedby=" + modifiedby
				+ "]";
	}

	

	

}
