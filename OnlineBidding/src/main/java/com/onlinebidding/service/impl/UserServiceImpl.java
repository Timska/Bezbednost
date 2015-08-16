package com.onlinebidding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.User;
import com.onlinebidding.repository.UserRepository;
import com.onlinebidding.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void create(User user) {
		userRepository.save(user);
	}

	public User findUser(String userName) {
		return userRepository.findOne(userName);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

}
