package com.onlinebidding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;
import com.onlinebidding.model.UserAuction;
import com.onlinebidding.repository.UserAuctionRepository;
import com.onlinebidding.service.UserAuctionService;

@Service
public class UserAuctionServiceImpl implements UserAuctionService {

	@Autowired
	private UserAuctionRepository userAuctionRepository;

	public void create(UserAuction userAuction) {
		userAuctionRepository.save(userAuction);
	}
	
	public void delete(String userName, Long auctionID) {
		UserAuction userAuction = userAuctionRepository.findByUserUserNameAndAuctionAuctionID(userName, auctionID);
		userAuctionRepository.delete(userAuction);
	}

	public UserAuction findUserAuctionByUserAndAuction(String userName, Long auctionID) {
		return userAuctionRepository.findByUserUserNameAndAuctionAuctionID(userName, auctionID);
	}
	
	public UserAuction findUserAuction(Long ID) {
		return userAuctionRepository.findOne(ID);
	}

	public List<UserAuction> getAllUserAuctions() {
		return userAuctionRepository.findAll();
	}

	public List<Auction> getUserEnteredAuctions(String userName) {
		List<UserAuction> userAuctions = userAuctionRepository.findByUserUserName(userName);
		List<Auction> auctions = new ArrayList<Auction>();
		for (UserAuction userAuction : userAuctions) {
			auctions.add(userAuction.getAuction());
		}
		return auctions;
	}

	public List<User> getAuctionEnteredUsers(Long auctionID) {
		List<UserAuction> userAuctions = userAuctionRepository.findByAuctionAuctionID(auctionID);
		List<User> users = new ArrayList<User>();
		for (UserAuction userAuction : userAuctions) {
			users.add(userAuction.getUser());
		}
		return users;
	}
	
}
