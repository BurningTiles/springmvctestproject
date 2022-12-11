package com.akshat.springmvc.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/test")
	public String test() {
		return "Server is running...";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "Hi there, How are you?";
	}
}
