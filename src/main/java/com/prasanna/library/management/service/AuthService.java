package com.prasanna.library.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prasanna.library.management.dto.LoginRequest;
import com.prasanna.library.management.dto.LoginResponse;
import com.prasanna.library.management.dto.UserDto;
import com.prasanna.library.management.exception.AuthenticationException;
import com.prasanna.library.management.exception.ConflictException;
import com.prasanna.library.management.exception.ResourceNotFoundException;
import com.prasanna.library.management.model.User;
import com.prasanna.library.management.repository.UserRepo;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public String register(UserDto userDto,String role) {
		
		if(userRepo.findByUsername(userDto.getUsername()).isPresent())
			throw new ConflictException("Username already exists!");
		
		if(userRepo.findByEmail(userDto.getEmail()).isPresent())
			throw new ConflictException("Email already exists!");
		
		User user=new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRole(role);
		
		userRepo.save(user);
		return "User registered successfully.";
	}
	
	public LoginResponse login(LoginRequest loginRequest) {
		
		User user=userRepo.findByUsername(loginRequest.getUsername()).orElseThrow(()->new ResourceNotFoundException("User not found!"));
		
		if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
			throw new AuthenticationException("Invalid Credentials!");
		
		LoginResponse loginResponse=new LoginResponse();
		loginResponse.setUsername(user.getUsername());
		loginResponse.setRole(user.getRole());
		
		return loginResponse;
	}
	
}
