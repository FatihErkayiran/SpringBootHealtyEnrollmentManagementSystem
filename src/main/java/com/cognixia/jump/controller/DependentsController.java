package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Dependents;
import com.cognixia.jump.repository.DependentsRepository;
import com.cognixia.jump.repository.EnrolleeRepository;



@RequestMapping("/api")
@RestController
public class DependentsController {
	
	@Autowired
	DependentsRepository repository;
	
	@Autowired
	EnrolleeRepository enrolleeRepository;

	
	@GetMapping("/dependents/{id}")
	public Dependents getDependentsById(@PathVariable long id) throws ResourceNotFoundException {
		Optional<Dependents>found=repository.findById(id);
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("Dependents with id= \" + id + \"not found in Database");
			
		}
		return found.get();
	}
	@GetMapping("/enrollee/{enrolleeId}/dependents")
	public List<Dependents> getAllDependentsByEnrolleeId(@PathVariable(value = "enrolleeId") long enrolleeId){
		return repository.findByEnrolleeId(enrolleeId);
	}

	
	@PostMapping("/enrollee/{enrollee_id}/add/dependents")
	public ResponseEntity<Dependents> addDependent(@PathVariable(value="enrollee_id") long enrolle_id, @Valid @RequestBody Dependents dependents) throws ResourceNotFoundException{
	   
	  return   enrolleeRepository.findById(enrolle_id).map(enrollee -> {
		  dependents.setEnrollee(enrollee);
		  Dependents addedDependents=repository.save(dependents);
		  return ResponseEntity.status(201).body(addedDependents);
	  }).orElseThrow(() -> new ResourceNotFoundException("enrollee is not found"));

		
	}
	
	
	@DeleteMapping("/enrollee/{enrolleeId}/dependents/delete/{id}")
	public ResponseEntity<String> deleteDependentsById(@PathVariable(value = "enrolleeId") long enrolleeId,
			@PathVariable(value = "id")long id) throws ResourceNotFoundException{
		return repository.findByIdAndEnrolleeId(id, enrolleeId).map(dependent -> {
			repository.delete(dependent);
			return ResponseEntity.status(200).body("dependent with id " + id + " has been deleted");
		}).orElseThrow(() -> new ResourceNotFoundException("enrollee and dependent are not found"));
		
	}
	
	@PutMapping("/enrollee/{enrollee_id}/dependents/update/{id}")
	public ResponseEntity<Dependents> updateDependents(@PathVariable(value = "enrollee_id") long enrollee_id,
			@PathVariable(value = "id")long id,@Valid@RequestBody Dependents dependents) throws ResourceNotFoundException{
		boolean found=enrolleeRepository.existsById(enrollee_id);
		if (!found) {
			throw new ResourceNotFoundException("Enrollee with id " + enrollee_id + "is not exists");
	
		}

		return repository.findById(id).map(dependent -> {
			dependent.setName(dependents.getName());
			dependent.setBirthdate(dependents.getBirthdate());
			Dependents updatedDependents=repository.save(dependent);
			return new ResponseEntity<Dependents>(updatedDependents,HttpStatus.OK);
		}).orElseThrow(()-> new ResourceNotFoundException("dependents is not found"));
		
	}
}
