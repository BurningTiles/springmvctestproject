package com.akshat.springmvc.repositories;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.akshat.springmvc.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	@Query("{$or:[{'name':{$regex:?0, $options:'i'}}, {'email':{$regex:?0, $options:'i'}}, {'country':{$regex:?0, $options:'i'}}, {'education':{$regex:?0, $options:'i'}}]}")
	public List<User> find(String keyword);
}