package com.prasanna.library.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prasanna.library.management.dto.LoginRequest;
import com.prasanna.library.management.dto.LoginResponse;
import com.prasanna.library.management.dto.UserDto;
import com.prasanna.library.management.exception.AuthenticationException;
import com.prasanna.library.management.exception.ConflictException;
import com.prasanna.library.management.model.User;
import com.prasanna.library.management.repository.UserRepo;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
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
		
		Authentication authentication=
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		if(!authentication.isAuthenticated())
			throw new AuthenticationException("Invalid credentials!");
		
		LoginResponse loginRespons = new LoginResponse();
		loginRespons.setToken(jwtService.generateToken(loginRequest.getUsername()));
		
		return loginRespons;
	}
	
}
