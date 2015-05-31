package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;
import com.onlinebidding.model.UserAuction;

public interface UserAuctionService {

	public void create(UserAuction userAuction);
	
	public void delete(Long ID);
	
	public UserAuction findUserAuction(Long ID);
	
	public List<UserAuction> getAllUserAuctions();
	
	public List<Auction> getUserEnteredAuctions(String userName);
	
	public List<User> getAuctionEnteredUsers(Long auctionID);
	
}
