package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@GetMapping("/user")
	public List<User>getAllUser(){
		return repository.findAll();
		
	}

	@PostMapping("/add/user")
	public User createUser(@RequestBody User user) {
		user.setId(-1L);
		User newUser=repository.save(user);
		
		return newUser;
	}
}
