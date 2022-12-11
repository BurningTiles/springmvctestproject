package com.akshat.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.springmvc.services.PageHitsService;

@RestController
public class PageHitController {
	
	@Autowired
	PageHitsService service;
	
	@GetMapping("/hits/{page}")
	public Integer pageHits(@PathVariable String page) {
		return service.getPageHits(page);
	}
}