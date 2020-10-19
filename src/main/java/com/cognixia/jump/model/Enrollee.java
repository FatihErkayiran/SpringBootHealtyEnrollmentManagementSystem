package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cognixia.jump.model.User.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

@Entity
@Table(name="enrollee")
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
	@NotNull
	private boolean activationStatus;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;
    private String telephoneNumber;
   
    
    
 //   @JsonManagedReference
    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.ALL)
    private List<Dependents>dependents;
	
	public Enrollee() {
	     this(-1L, "N/A", false,LocalDate.now(), "N/A", new ArrayList<>());
	}

	public Enrollee(long id, @NotBlank String name, @NotNull boolean activationStatus, @NotNull LocalDate birthDate,
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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
		//this.dependents = dependents;
		for (int i = 0; i < dependents.size(); i++) {
			Dependents dep=dependents.get(i);
			dep.setEnrollee(this);
		    this.dependents.add(dep);	
		}
	}

	@Override
	public String toString() {
		return "Enrollee [id=" + id + ", name=" + name + ", activationStatus=" + activationStatus + ", birthDate="
				+ birthDate + ", telephoneNumber=" + telephoneNumber + ", dependents=" + dependents + "]";
	}

	
	
	
	
	
	
	
}
