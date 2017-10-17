package com.bandme.service;

import com.bandme.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
