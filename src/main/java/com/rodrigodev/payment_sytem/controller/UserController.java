package com.rodrigodev.payment_sytem.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigodev.payment_sytem.dto.AuthenticationRequest;
import com.rodrigodev.payment_sytem.dto.AuthenticationResponse;
import com.rodrigodev.payment_sytem.dto.UserRequest;
import com.rodrigodev.payment_sytem.dto.UserResponse;
import com.rodrigodev.payment_sytem.entity.User;
import com.rodrigodev.payment_sytem.service.TokenService;
import com.rodrigodev.payment_sytem.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) throws UnsupportedEncodingException, MessagingException{
		User user = userRequest.toModel();
		UserResponse userSaved = userService.resgisterUser(user);
		return ResponseEntity.ok().body(userSaved);
	}
	
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
		if(userService.verify(code)) {
			return "verify_sucess";
		} else {
			return "verify_fail";
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequest.email(), authenticationRequest.password());
		var auth = authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new AuthenticationResponse(token));	
	}
	
	@GetMapping("/teste")
    public String teste(){
        return "você está logado";
    }
}
