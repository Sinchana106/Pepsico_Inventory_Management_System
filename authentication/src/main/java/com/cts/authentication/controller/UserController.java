package com.cts.authentication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.cts.authentication.exception.InternalServerException;
import com.cts.authentication.exception.UserNameNumericException;
import com.cts.authentication.exception.UserNotFoundException;
import com.cts.authentication.model.User;
import com.cts.authentication.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping
	public  ResponseEntity<User> registerUser(@Valid @RequestBody User user) throws InternalServerException {
		
		try {
			if(!service.validateUserName(user.getName())) {
				throw new UserNameNumericException("Name should not contain numerics");
			}
			return new ResponseEntity<User>(service.registerUser(user),HttpStatus.CREATED);
		}
		catch(InternalServerError | NullPointerException e) {
			throw new InternalServerException("Database connectivity Issue");
		}
	}
	@GetMapping
	public  ResponseEntity<Boolean> login(@RequestParam String username,@RequestParam String password ) {
		Boolean response = service.loginUser(username, password);
		if(!response)
			throw new UserNotFoundException("Username or password is incorrect");
		return new ResponseEntity<Boolean>(response,HttpStatus.OK);
	}

}
