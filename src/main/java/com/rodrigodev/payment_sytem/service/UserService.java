package com.rodrigodev.payment_sytem.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rodrigodev.payment_sytem.util.RandomString;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import com.rodrigodev.payment_sytem.dto.UserResponse;
import com.rodrigodev.payment_sytem.entity.User;
import com.rodrigodev.payment_sytem.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private MailService mailService;
	
	@Transactional
	public UserResponse resgisterUser(User user) throws UnsupportedEncodingException, MessagingException {
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new RuntimeException("This email already exists");
		} else {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			
			String randomCode = RandomString.generateRandomString(64);
			user.setVerificationCode(randomCode);
			user.setEnabled(false);
			
			User savedUser = userRepository.save(user);
			
			UserResponse userResponse = new UserResponse(
					savedUser.getId(),
					savedUser.getName(),
					savedUser.getEmail(),
					savedUser.getPassword()
			);
			mailService.sendVerificationEmail(user);
			return userResponse;
		}
	}
	
	public boolean verify(String verificationCode) {
		User user = userRepository.findByVerificationCode(verificationCode);
		
		if(user == null || user.isEnabled()) {
			return false;
		} else {
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);
			
			return true;
		}
	}
}
