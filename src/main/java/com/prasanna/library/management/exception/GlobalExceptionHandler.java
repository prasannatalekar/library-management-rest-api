package com.prasanna.library.management.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception,HttpServletRequest request){
		ErrorResponse errorResponse=new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				exception.getMessage(),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception,HttpServletRequest request){
		ErrorResponse errorResponse=new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				exception.getMessage(),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponse> handleConflictException(ConflictException exception,HttpServletRequest request){
		ErrorResponse errorResponse=new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.CONFLICT.value(),
				exception.getMessage(),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception,HttpServletRequest request){
		ErrorResponse errorResponse=new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				exception.getMessage(),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.UNAUTHORIZED);
	}
	
}
