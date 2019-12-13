package com.nest.appuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nest.appuser.entity.User;
import com.nest.appuser.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	@Autowired // is to not do a new....
	UserRepository repository;

	@Override
	public Iterable<User> getAllUserss() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	

}
