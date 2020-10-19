package com.cognixia.jump.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.repository.EnrolleeRepository;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class EnrolleeController {
	
	
	@Autowired
    EnrolleeRepository repository;
	
	@ApiOperation(value = "Find all Enrollee",
	      	  	  notes="Provides information to look up all enrollee in the db. ",
	      	      response = Enrollee.class)
	@GetMapping("/enrollees")
	public List<Enrollee> getAllEnrollees(){
	
			return repository.findAll();
		
		
	}
	
	@ApiOperation(value = "Find an Enrollee by its id",
		      	  notes="Provides information to look up a enrollee in the db.If id not found,it will return 404 ",
		      	  response = Enrollee.class)
	
	@GetMapping("/enrollee/{id}")
	public Enrollee getEnrolleeById(@PathVariable long id ) throws ResourceNotFoundException {
		Optional<Enrollee>found=repository.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Enrollee with id " + id + " not found " );
		}
		return found.get();
	}
	
	@ApiOperation(value = "Add an Enrollee by its id",
	      	  	 notes="Used to add an enrollee ",
	      	  	 response = Enrollee.class)
	
	@PostMapping("/add/enrollee")
	public ResponseEntity<Enrollee> addEnrollee(@RequestBody Enrollee enrollee) {
		enrollee.setId(-1L);
		Enrollee newEnrollee=repository.save(enrollee);
		return new ResponseEntity<>(newEnrollee,HttpStatus.CREATED);
		
		
	}
	@ApiOperation(value = "Delete an Enrollee by its id",
				  notes="Used to delete an enrollee.If id not found,it will return 404 ",
				  response = Enrollee.class)
	@DeleteMapping("/delete/enrollee/{id}")
	public ResponseEntity<String> deleteEnrolleeById(@PathVariable long id){
		Optional<Enrollee>found=repository.findById(id);
		if (found.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.status(200).body("Enrollee with id " + id +" is deleted succesfully");
		}
		else {
			return ResponseEntity.status(404)
					.header("enrolleeId", id + "")
					.body("enrolleeID with id " + id + "not found");
		}
	}
	
	@ApiOperation(value = "Update an Enrollee",
			  notes="Used to update an enrollee.If id not found,it will return 404 ",
			  response = Enrollee.class)
	@PutMapping("/update/enrollee")
	public ResponseEntity<Enrollee> updateEnrolleeById(@RequestBody Enrollee enrollee) throws ResourceNotFoundException{
		boolean found=repository.existsById(enrollee.getId());
		if (!found) {
			throw new ResourceNotFoundException("Enrollee with id "+ enrollee.getId() + " not found");
			
		}
		Enrollee enrollee2=repository.save(enrollee);
		return new ResponseEntity<Enrollee>(enrollee2,HttpStatus.OK);
	}
	
	
	
	
	
	
	
}
