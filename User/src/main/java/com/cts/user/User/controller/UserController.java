package com.cts.user.User.controller;

import com.cts.user.User.entity.User;
import com.cts.user.User.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.PostConstruct;

@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public ResponseEntity<User> registerNewUser(@RequestBody User user) throws Exception {
    	if(userService.isUserPresent(user)) {
    		throw new Exception("User already registered");
    	}
    	if(user.getUserFirstName()!=null) {
    		throw new Exception("User's First Name cannot be empty");
    	}
    	if(user.getUserLastName()!=null) {
    		throw new Exception("User's Last Name cannot be empty");
    	}	
    	if(user.getContact()!=null) {
    		throw new Exception("User's Contact number cannot be empty");
    	}
    	if(user.getDob()!=null) {
    		throw new Exception("User's DOB number cannot be empty");
    	}
    
      if(user.getUserPassword()!=null) {
    	  throw new Exception("User's Password number cannot be empty");
	}
        return new ResponseEntity<User> (userService.registerNewUser(user),HttpStatus.CREATED);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
    	return new ResponseEntity<List<User>>( userService.getAllUsers(),HttpStatus.OK);
    }
    
    @GetMapping("/users/{userName}")
    public ResponseEntity<User> getUsersById(@PathVariable String userName){
    	return new ResponseEntity<User>( userService.getUsersById(userName),HttpStatus.OK);
    }
    
}