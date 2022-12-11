package com.akshat.springmvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akshat.springmvc.models.User;
import com.akshat.springmvc.repositories.UserRepository;
import com.akshat.springmvc.services.PageHitsService;

import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PageHitsService service;

	@GetMapping("/add")
	public String addUserForm(Model model) {
		service.pageCalled("add");
		model.addAttribute("user", new User());
		return "add-user";
	}

	@PostMapping("/add")
	public String addUser(@Valid User user, BindingResult result, Model model,
			RedirectAttributes redirAttrs) {
		model.addAttribute("user", user);
		if (result.hasErrors())
			return "add-user";
		userRepo.save(user);
		redirAttrs.addFlashAttribute("message", "User added successfully");
		return "redirect:/home";
	}

	@GetMapping({ "/", "/home", "/index" })
	public String home(Model model) {
		service.pageCalled("home");
		return "home";
	}

	@GetMapping("/users")
	public String users(@RequestParam(required = false) String search,Model model) {
		service.pageCalled("users");
		model.addAttribute("search", search==null ? "" : search);
		if(search==null || search.length()==0)
			model.addAttribute("users", userRepo.findAll());
		else
			model.addAttribute("users", userRepo.find(search));
		return "users";
	}

	@GetMapping("/edit/{id}")
	public String updateUserForm(@PathVariable String id, Model model, RedirectAttributes redirAttrs) {
		service.pageCalled("edit");
		User user;
		if(userRepo.existsById(id)) {
			user = userRepo.findById(id).get();
		} else {
			redirAttrs.addFlashAttribute("message", "User not found");
			return "redirect:/users";
		}
		
		model.addAttribute("user", user);
		return "edit-user";
	}

	@PostMapping("/edit/{id}")
	public String updateUser(@PathVariable String id, @Valid User user,
			BindingResult result, Model model, RedirectAttributes redirAttrs) {
		if (result.hasErrors()) {
			user.setId(id);
			return "edit-user";
		}

		userRepo.save(user);
		redirAttrs.addFlashAttribute("message", "User updated");
		return "redirect:/users";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable String id, Model model,
			RedirectAttributes redirAttrs) {
		if (userRepo.existsById(id)) {
			userRepo.deleteById(id);
			redirAttrs.addFlashAttribute("message", "User deleted");
		}
		else
			redirAttrs.addFlashAttribute("message", "User not found");
		return "redirect:/users";
	}
}