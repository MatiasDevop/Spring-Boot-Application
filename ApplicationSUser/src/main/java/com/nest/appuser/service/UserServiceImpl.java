package com.nest.appuser.service;

import java.util.Optional;

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
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if(userFound.isPresent()) {
			throw new Exception("Username unable");
		}
		return true;
	}
	private boolean checkPasswordMatch(User user) throws Exception
	{
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Confirm password is must");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("password and confirm are not equals");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		// TODO Auto-generated method stub
		if(checkUsernameAvailable(user) && checkPasswordMatch(user)) {
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new Exception("User to edit dosen't exist."));
	}

	@Override
	public User updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		User toUser = getUserById(user.getId());
		mapUser(user, toUser);
		return repository.save(toUser);
		
	}
	 
	/**
	 * Map everythin but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from, User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}

}
