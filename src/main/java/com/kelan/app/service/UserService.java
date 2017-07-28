package com.kelan.app.service;

import java.util.List;
import java.util.Set;

import com.kelan.app.entity.User;

public interface UserService {

    User save(User user);

    Set<String> getStringRoles(String username);

    Set<String> getStringPermissions(String username);

	List<User> findAllUsers();
	
	User findUserByName(String name);
	
	User findByUsername(String username);

}
