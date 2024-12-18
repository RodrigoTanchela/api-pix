package com.rodrigodev.payment_sytem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.rodrigodev.payment_sytem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByEmail(String email);
	
	User findByVerificationCode(String verificationCode);
}
