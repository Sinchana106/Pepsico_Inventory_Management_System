package com.cts.user.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.user.User.entity.JwtRequest;
import com.cts.user.User.entity.JwtResponse;
import com.cts.user.User.entity.User;
import com.cts.user.User.service.JwtService;
import com.cts.user.User.service.UserService;

@RestController
@CrossOrigin("http://localhost:4200")
public class JwtController {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserService userService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
    	//String userName=jwtRequest.getUserName();
    	//String password=jwtRequest.getUserPassword();
    	//User userObj=null;
    	//if(userName!=null && password!=null) {
    	//	userObj=userService.fetchByUserNameAndPassword(userName, password);
    	//}
    	//if(userObj==null) {
    	//	throw new Exception("Bad Credentials!");
    	//}	
     return jwtService.createJwtToken(jwtRequest);
    }
}