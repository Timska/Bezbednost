package com.onlinebidding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Auction;
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

	public void enterAuction(Auction auction, String userName) {
		User user = findUser(userName);
		user.getEnteredAuctions().add(auction);
		userRepository.save(user);
	}

	public void exitAuction(Auction auction, String userName) {
		User user = findUser(userName);
		user.getEnteredAuctions().remove(auction);
		userRepository.save(user);
	}

}
