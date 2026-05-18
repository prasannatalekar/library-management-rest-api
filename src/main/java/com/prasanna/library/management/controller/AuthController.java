package com.prasanna.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prasanna.library.management.dto.LoginRequest;
import com.prasanna.library.management.dto.LoginResponse;
import com.prasanna.library.management.dto.UserDto;
import com.prasanna.library.management.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register/user")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto){
		return ResponseEntity.ok(authService.register(userDto,"ROLE_USER"));
	}
	
	@PostMapping("/register/admin")
	public ResponseEntity<String> registerAdmin(@RequestBody UserDto userDto){
		return ResponseEntity.ok(authService.register(userDto,"ROLE_ADMIN"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	
}
