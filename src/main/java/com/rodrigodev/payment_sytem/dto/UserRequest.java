package com.rodrigodev.payment_sytem.dto;

import com.rodrigodev.payment_sytem.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
		@NotNull(message = "O nome não pode ser nulo")
		@NotBlank(message = "O nome não pode ser vazio")
		String name,
		
		@NotNull(message = "O email não pode ser nulo")
		@NotBlank(message = "O email não pode ser vazio")
		@Email 
		String email,
		
		@NotNull(message = "A senha não pode ser nula")
		@NotBlank(message = "A senha não pode estar vazia")
		@Size(min = 8, message = "A senha deve conter no minimo 8 caracteres")
		String password,
		
		@NotNull(message = "A role não pode ser nula")
		@NotBlank(message = "A role não pode estar vazia")
		String role) {
	
	public User toModel() {
		return new User(name,email,password,role);
	}
}
