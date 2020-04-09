package com.revature.organization.model;




import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "institution")
public class Organization  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="`name`")
	@NotEmpty(message="Name Cannot be Empty")
	private String name;
	@Column(name="`alias_name`")
	@NotEmpty(message="Alias Cannot be Empty")
	private String alias;
	@Column(name="`university`")
	@NotEmpty(message="University Cannot be Empty")
	private String university;
	@Column(name="`created_on`")
	 private LocalDateTime createdon;
	@Column(name="`modified_on`")
	private LocalDateTime modifiedon;
	@Column(name="`isactive`")
	private Boolean isActive=true;
	

	

	
	

	



	public Organization(int id, @NotEmpty(message = "Name Cannot be Empty") String name,
			@NotEmpty(message = "Alias Cannot be Empty") String alias,
			@NotEmpty(message = "University Cannot be Empty") String university, Boolean isActive) {
		super();
		this.id = (long)id;
		this.name = name;
		this.alias = alias;
		this.university = university;
		this.isActive = isActive;
	}



	public Organization() {
		// TODO Auto-generated constructor stub
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	





	@Override
	public String toString() {
		return "Organization [id=" + id + ", name=" + name + ", alias=" + alias + ", university=" + university
				+ ", createdon=" + createdon + ", modifiedon=" + modifiedon + ", isActive=" + isActive + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Organization other = (Organization) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}




	

	
	
	

	

	

	



}
