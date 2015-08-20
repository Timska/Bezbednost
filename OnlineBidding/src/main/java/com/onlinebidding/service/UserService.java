package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.User;

public interface UserService {

	public void create(User user);
	
	public void delete(User user);

	public User findUser(String userName);

	public List<User> getAllUsers();
	
	public User updateUser(String userName, String resId);

}
