package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Enrollee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5290265595702928345L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	private String name;
	@NotEmpty
	private boolean activationStatus;
    @NotBlank
	private Date birthDate;
    private String telephoneNumber;
    
    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.ALL)
    private List<Dependents>dependents;
	
	public Enrollee() {
	
	}

	public Enrollee(long id, @NotBlank String name, @NotEmpty boolean activationStatus, @NotBlank Date birthDate,
			String telephoneNumber, List<Dependents> dependents) {
		super();
		this.id = id;
		this.name = name;
		this.activationStatus = activationStatus;
		this.birthDate = birthDate;
		this.telephoneNumber = telephoneNumber;
		this.dependents = dependents;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public List<Dependents> getDependents() {
		return dependents;
	}

	public void setDependents(List<Dependents> dependents) {
		this.dependents = dependents;
	}

	
	
	
	
	
	
	
}
