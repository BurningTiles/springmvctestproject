package com.akshat.springmvc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.akshat.springmvc.models.User;
import com.akshat.springmvc.repositories.UserRepository;
import com.akshat.springmvc.services.PageHitsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringMvcUsersApplicationTests {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserRepository repo;
	
	@Autowired
	private PageHitsService service;
	
	static private List<User> noUser, users;
	static private User noName, invalidName, noEmail, invalidEmail, noEdu, validUser; 
	
	ObjectMapper mapper = new ObjectMapper();
	
	@BeforeAll
	static void init() {
		noUser = new ArrayList<User>();
		users = new ArrayList<User>();
		users.add(new User("u1", "TestUserOne", "test1@abc.com", "World", "B.Tech"));
		users.add(new User("u2", "TestUserTwo", "test2@abc.com", "World", "B.Tech"));
		users.add(new User("u3", "TestUserThree", "test3@abc.com", "World", "B.Tech"));
		noName = new User("u4", "", "test@abc.com", "World", "B.Tech");
		invalidName = new User("u4", "test1", "test@abc.com", "World", "B.Tech");
		noEmail = new User("u4", "email", "", "World", "B.Tech");
		invalidEmail = new User("u4", "email", "test", "World", "B.Tech");
		noEdu = new User("u4", "education", "test@abc.com", "World", "");
		validUser = new User("u123", "User", "user@abc.com", "World", "B.Tech");
	}
	
	static String toFormData(User user) {
		StringBuilder ans = new StringBuilder();
		try {
			ans.append("id=" + URLEncoder.encode(user.getId(), StandardCharsets.UTF_8.name()));
			ans.append("&name=" + URLEncoder.encode(user.getName(), StandardCharsets.UTF_8.name()));
			ans.append("&email=" + URLEncoder.encode(user.getEmail(), StandardCharsets.UTF_8.name()));
			ans.append("&country=" + URLEncoder.encode(user.getCountry(), StandardCharsets.UTF_8.name()));
			ans.append("&education=" + URLEncoder.encode(user.getEducation(), StandardCharsets.UTF_8.name()));
		}
		catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
			return "";
		}
		return ans.toString();
	}
	
	@Test
	@Order(1)
	public void test_home_page_hits_service() throws Exception {
		assertEquals(service.getPageHits("home"), 0);
		mvc.perform(get("/home"));
		assertEquals(service.getPageHits("home"), 1);
		mvc.perform(get("/home"));
		assertEquals(service.getPageHits("home"), 2);
	}
	
	@Test
	@Order(2)
	public void test_users_page_hits_service() throws Exception {
		assertEquals(service.getPageHits("users"), 0);
		mvc.perform(get("/users"));
		assertEquals(service.getPageHits("users"), 1);
		mvc.perform(get("/users"));
		assertEquals(service.getPageHits("users"), 2);
	}
	
	@Test
	@Order(3)
	public void test_add_page_hits_service() throws Exception {
		assertEquals(0, service.getPageHits("add"));
		mvc.perform(get("/add"));
		assertEquals(1, service.getPageHits("add"));
		mvc.perform(get("/add"));
		assertEquals(2, service.getPageHits("add"));
	}
	
	@Test
	@Order(4)
	public void test_all_page_hits_service() throws Exception {
		mvc.perform(get("/home"));
		mvc.perform(get("/users"));
		mvc.perform(get("/add"));
		assertEquals(3, service.getPageHits("home"));
		assertEquals(3, service.getPageHits("users"));
		assertEquals(3, service.getPageHits("add"));
	}
	
	@Test
	@Order(5)
	public void test_hits_api_user() throws Exception {
		mvc.perform(get("/hits/home"))
				.andExpect(content().string("3"));
		mvc.perform(get("/hits/users"))
			.andExpect(content().string("3"));
		mvc.perform(get("/hits/add"))
			.andExpect(content().string("3"));
	}
	
	@Test
	public void test_no_users_page_data() throws Exception {
		Mockito.when(repo.findAll()).thenReturn(noUser);
		MvcResult result = mvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("No users"));
	}
	
	@Test
	public void test_users_page_data() throws Exception {
		Mockito.when(repo.findAll()).thenReturn(users);
		MvcResult result = mvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andReturn();
		String data = result.getResponse().getContentAsString();
		assertFalse(data.contains("No users"));
		assertTrue(data.contains("TestUserOne"));
		assertTrue(data.contains("test1@abc.com"));
		assertTrue(data.contains("B.Tech"));
		assertTrue(data.contains("TestUserTwo"));
		assertTrue(data.contains("test2@abc.com"));
		assertTrue(data.contains("TestUserThree"));
		assertTrue(data.contains("test3@abc.com"));
	}
	
	@Test
	public void test_no_user_name() throws Exception {
		Mockito.when(repo.save(noName)).thenReturn(noName);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(noName)))
				.andExpect(status().isOk())
				.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("Name is mandatory"));
		assertFalse(data.contains("User added"));
	}
	
	@Test
	public void test_invalid_user_name() throws Exception {
		Mockito.when(repo.save(invalidName)).thenReturn(invalidName);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(invalidName)))
				.andExpect(status().isOk())
				.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("Invalid name"));
		assertFalse(data.contains("User added"));
	}
	
	@Test
	public void test_no_email() throws Exception {
		Mockito.when(repo.save(noEmail)).thenReturn(noEmail);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(noEmail)))
				.andExpect(status().isOk())
				.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("Email is mandatory"));
		assertFalse(data.contains("User added"));
	}
	
	@Test
	public void test_invalid_email() throws Exception {
		Mockito.when(repo.save(invalidEmail)).thenReturn(invalidEmail);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(invalidEmail)))
				.andExpect(status().isOk())
				.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("Invalid email"));
		assertFalse(data.contains("User added"));
	}
	
	@Test
	public void test_no_education() throws Exception {
		Mockito.when(repo.save(noEdu)).thenReturn(noEdu);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(noEdu)))
				.andExpect(status().isOk())
				.andReturn();
		String data = result.getResponse().getContentAsString();
		assertTrue(data.contains("Education is mandatory"));
		assertFalse(data.contains("User added"));
	}
	
	@Test
	public void test_valid_user() throws Exception {
		Mockito.when(repo.save(validUser)).thenReturn(validUser);
		MvcResult result = mvc.perform(post("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
	            .content(toFormData(validUser)))
				.andExpect(redirectedUrl("/home"))
				.andExpect(status().isFound())
				.andReturn();
	}
}
