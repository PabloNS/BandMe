package com.bandme.service;

import com.bandme.model.Role;
import com.bandme.model.User;

public interface UserService {
	public User findUserById(Long id);
	public User findUserByEmail(String email);
	public void registerUser(User user);
	public void saveUser(User user);
	public void saveAdmin(User user);
	public void saveRole(Role role);
}
