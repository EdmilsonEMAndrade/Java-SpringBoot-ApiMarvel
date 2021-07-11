package br.com.desafio.orange.talents.controller.exception.handler;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.desafio.orange.talents.service.exception.ValidationException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> noValuePresent(NoSuchElementException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), "No data found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);		
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<StandardError> validation(ValidationException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StandardError> failedToConvert(MethodArgumentTypeMismatchException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Could not convert the parameter");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}	
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<StandardError> notFound(HttpClientErrorException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), "Comic not found.");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){		
		ValidationErrorBean err = new ValidationErrorBean(HttpStatus.BAD_REQUEST.value(), "Validation error.");
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> validationConstructor(ConstraintViolationException e, HttpServletRequest request){		
		ValidationErrorBean err = new ValidationErrorBean(HttpStatus.BAD_REQUEST.value(), "Validation error!");
		for(ConstraintViolation<?> x : e.getConstraintViolations()) {
			err.addError(x.getPropertyPath().toString(), x.getMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<StandardError> dataError(HttpMessageNotReadableException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Date format is wrong. yyyy-MM-dd");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataError(DataIntegrityViolationException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "CPF or e-mail already registered.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<StandardError> includError(InvalidDataAccessApiUsageException e, HttpServletRequest request){		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Comic is already user related.");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);		
	}
	
}
