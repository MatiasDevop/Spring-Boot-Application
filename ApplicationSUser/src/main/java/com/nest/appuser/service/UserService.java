package com.nest.appuser.service;

import javax.validation.Valid;

import com.nest.appuser.entity.User;

public interface UserService {
	
	public Iterable<User> getAllUserss();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User user) throws Exception;
}
