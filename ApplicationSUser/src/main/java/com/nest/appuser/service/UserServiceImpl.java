package com.nest.appuser.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nest.appuser.Exception.UsernameOrIdNotFound;
import com.nest.appuser.dto.ChangePasswordForm;
import com.nest.appuser.entity.User;
import com.nest.appuser.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	@Autowired // is to not do a new....
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

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
			
			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);// to encryp passwot at create user
			
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		return repository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("The ID user dosen't exist."));
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

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')") // this is to acces services rest 
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		// TODO Auto-generated method stub
		User user = getUserById(id);
		repository.delete(user);
	}

	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		// TODO Auto-generated method stub
		User user = getUserById(form.getId());
		
		if(!isLoggedUserADMIN() && !user.getPassword().equals(form.getCurrentPassword())) {
			throw new Exception("Current Password invalid. ");
		}
		if(user.getPassword().equals(form.getNewPassword())) {
			throw new Exception("New it must be difirent at before");
		}
		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("New password and current password not matching");
		}
		// to encrypt password when change it
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);
		return repository.save(user);
		
	}
	public boolean isLoggedUserADMIN(){
		 return loggedUserHasRole("ROLE_ADMIN");
	}
	public boolean loggedUserHasRole(String role) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();// to get user recorded in session
		UserDetails loggedUser = null;
		Object roles = null; 
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		
			roles = loggedUser.getAuthorities().stream()
					.filter(x -> role.equals(x.getAuthority() ))      
					.findFirst().orElse(null); //loggedUser = null;
		}
		return roles != null ?true :false;
	}
	

}
