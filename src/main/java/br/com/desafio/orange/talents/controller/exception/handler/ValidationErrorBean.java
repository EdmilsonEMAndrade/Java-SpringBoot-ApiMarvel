package br.com.desafio.orange.talents.controller.exception.handler;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorBean extends StandardError{
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationErrorBean(Integer status, String message) {
		super(status, message);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
	
}
