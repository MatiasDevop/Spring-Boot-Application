package com.nest.appuser.Exception;

public class UsernameOrIdNotFound extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7788518674060161670L;
	
	public UsernameOrIdNotFound() {
		super("Usuario o Id not Founded");
	}
	
	public UsernameOrIdNotFound(String message) {
		super(message);
	}

}
