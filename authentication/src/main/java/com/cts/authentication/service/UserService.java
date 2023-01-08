
package com.cts.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.cts.authentication.model.MyUser;

public interface UserService {

	public UserDetails loadByUserName(String username);

	public MyUser registerUser(MyUser user);

	public boolean loginUser(String username, String password);

	public boolean validateUserName(String name);
}
