package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserRepository service;

	@GetMapping("/users")
	public List<User> getAllUsers() {

		return service.findAll();
	}

	@GetMapping("/user/{userId}")
	public User getByUserId(@PathVariable long userId) throws Exception {

		Optional<User> optional = service.findById(userId);

		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("U = " + userId + " not found");
		} else
			return optional.get();

	}

	@GetMapping("/login/{username}/{password}")
	public User validLogin(@PathVariable String username, @PathVariable String password)
			throws Exception, ResourceNotFoundException {

		Optional<User> userFound = service.findByUsername(username);

		if (!userFound.isPresent()) {
			throw new UsernameNotFoundException("User with username = " + username + " does not exist");
		} else if (userFound.get().getPassword().contentEquals(password)) {
			return userFound.get();
		}

		// TODO: could make another custom exception
		throw new Exception("Invalid password!");
	}

	@PostMapping("/user/add")
	public void addUser(@RequestBody User newUser) {
		service.save(newUser);
	}

	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable long userId) {

		Optional<User> found = service.findById(userId);

		if (found.isPresent()) {

			service.deleteById(userId);
			return ResponseEntity.status(200).body("Deleted user with id = " + userId);
		} else {
			return ResponseEntity.status(400).header("user id", userId + "")
					.body("User with id = " + userId + " not found");
		}

	}
}

