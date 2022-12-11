package com.akshat.springmvc.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private String id;
	@Pattern(regexp = "^[a-zA-Z\s]*", message = "Invalid name")
	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email id")
	private String email;
	private String country;
	@NotBlank(message = "Education is mandatory")
	private String education;
}