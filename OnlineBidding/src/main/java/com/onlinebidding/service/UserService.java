package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;

public interface UserService {

	public void create(User user);
	
	public User findUser(String userName);
	
	public List<User> getAllUsers();
	
	public void enterAuction(Auction auction, String userName);
	
	public void exitAuction(Auction auction, String userName);
}
