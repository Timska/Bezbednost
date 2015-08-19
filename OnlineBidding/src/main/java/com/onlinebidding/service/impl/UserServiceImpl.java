package com.onlinebidding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.User;
import com.onlinebidding.model.UserAuction;
import com.onlinebidding.repository.UserAuctionRepository;
import com.onlinebidding.repository.UserRepository;
import com.onlinebidding.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAuctionRepository userAuctionRepository;
	
	public void create(User user) {
		userRepository.save(user);
	}

	public User findUser(String userName) {
		return userRepository.findOne(userName);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Data Integrity problem
	public void delete(User user) {
		List<UserAuction> userAuctions = userAuctionRepository.findByUserUserName(user.getUserName());
		for (UserAuction ua : userAuctions) {
			userAuctionRepository.delete(ua.getID());
		}
		userRepository.delete(userRepository.findOne(user.getUserName()));
	}

}
