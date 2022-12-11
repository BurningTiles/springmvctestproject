package com.akshat.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringMvcUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcUsersApplication.class, args);
	}

}
