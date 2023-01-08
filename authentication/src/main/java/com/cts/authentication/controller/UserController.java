package com.cts.authentication.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.cts.authentication.config.JwtUtil;
import com.cts.authentication.exception.InternalServerException;
import com.cts.authentication.exception.UserNameNumericException;
import com.cts.authentication.exception.UserNotFoundException;
import com.cts.authentication.model.MyUser;
import com.cts.authentication.model.UserCredentials;
import com.cts.authentication.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	final static Logger log = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService service;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public  ResponseEntity<MyUser> registerUser(@Valid @RequestBody MyUser user) throws InternalServerException {
		
		try {
			if(!service.validateUserName(user.getUserName())) {
				throw new UserNameNumericException("Name should not contain numerics");
			}
			return new ResponseEntity<MyUser>(service.registerUser(user),HttpStatus.CREATED);
		}
		catch(InternalServerError | NullPointerException e) {
			throw new InternalServerException("Database connectivity Issue");
		}
	}
	@GetMapping("/validate")
	public ResponseEntity<Boolean> validate(@RequestHeader(name = "Authorization") String token1) {
		String token = token1.substring(7);
		try {
			log.info("inside validation................................");
			UserDetails user = service.loadByUserName(jwtUtil.extractUsername(token));
			log.info("after validation................................");
			
			if (jwtUtil.validateToken(token, user)) {
				System.out.println("=================Inside Validate==================");
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * Method to check whether login credentials are correct or not
	 * 
	 * @param userCredentials user credentials contain user name and password
	 * @return This returns token on successful login else throws exception
	 */
	@PostMapping("/login")
	public String login(@RequestBody UserCredentials userCredentials) {

		if (userCredentials.getUserName() == null || userCredentials.getPassword() == null
				|| userCredentials.getUserName().trim().isEmpty() || userCredentials.getPassword().trim().isEmpty()) {
			log.debug("Login unsuccessful --> User name or password is empty");
			throw new UserNotFoundException("User name or password cannot be Null or Empty");
		}

		else if (jwtUtil.isNumeric(userCredentials.getUserName())) {
			log.debug("Login unsuccessful --> User name is numeric");
			throw new UserNameNumericException("User name is numeric");
		}

		else {
			try {
				UserDetails user = service.loadByUserName(userCredentials.getUserName());
				
				if (user.getPassword().equals(userCredentials.getPassword())) {
					String token = jwtUtil.generateToken(user.getUsername());
					System.out.println(token);
					log.debug("Login successful");
					return token;
				} else {
					log.debug("Login unsuccessful --> Invalid password");
					throw new UserNotFoundException("Password is wrong");
				}
			} catch (Exception e) {
				//System.out.println("user.getUsername()-"+user.getUsername());
				//UserDetails user = service.loadByUserName(userCredentials.getUserName());
				//System.out.println("userCredentials.getUsername()-"+userCredentials.getUserName());
				//System.out.println("userCredentials.Password()-"+userCredentials.getPassword());
				//System.out.println(user);
				log.debug("Login unsuccessful --> Invalid Credential");
				throw new UserNotFoundException("Invalid Credential");
			}
		}
	}

}
