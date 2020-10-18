package com.cognixia.jump.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dependents")
public class Dependents implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1362491503369341375L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	private String name;
	@NotNull
	private LocalDate birthdate;
	
	@JsonIgnore
//    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "enrollee_id", referencedColumnName="id")
	private Enrollee enrollee;
	
	
	public Dependents() {
		this(-1L, "N/A", LocalDate.now());
	}

	public Dependents(long id, @NotBlank String name, @NotNull LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
		
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Enrollee getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Enrollee enrollee) {
		this.enrollee = enrollee;
	}

	@Override
	public String toString() {
		return "Dependents [id=" + id + ", name=" + name + ", birthdate=" + birthdate + ", enrollee=" + enrollee + "]";
	}

	
}
