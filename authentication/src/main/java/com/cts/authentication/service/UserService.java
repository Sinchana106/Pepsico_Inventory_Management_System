
package com.cts.authentication.service;

import com.cts.authentication.model.User;

public interface UserService {

	public User registerUser(User user);
	public boolean loginUser(String username,String password);
	public boolean validateUserName(String name);
}

