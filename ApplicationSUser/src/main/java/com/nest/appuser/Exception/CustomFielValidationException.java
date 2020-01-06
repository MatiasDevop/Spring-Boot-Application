package com.nest.appuser.Exception;

public class CustomFielValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4846297925350370125L;
	
	private String fieldName;
	
	public CustomFielValidationException(String message, String fieldName) {
		super(message);
		this.fieldName = fieldName;
		
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

}
