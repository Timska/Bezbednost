package com.onlinebidding.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.Item;
import com.onlinebidding.model.User;
import com.onlinebidding.repository.AuctionRepository;
import com.onlinebidding.service.AuctionService;

@Service
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionRepository auctionRepository;
	
	public void create(Auction auction) {
		auctionRepository.save(auction);
	}

	public Auction findAuctionByName(String auctionName) {
		return auctionRepository.findOne(auctionName);
	}

	public User getAuctionCreator(String auctionName) {
		return findAuctionByName(auctionName).getCreator();
	}

	public Item getAuctionItem(String auctionName) {
		return findAuctionByName(auctionName).getItem();
	}

	public Date getAuctionStartDate(String auctionName) {
		return findAuctionByName(auctionName).getStartDate();
	}

	public Date getAuctionEndDate(String auctionName) {
		return findAuctionByName(auctionName).getEndDate();
	}

	public List<Auction> getUserAuctions(String userName) {
		return auctionRepository.findByCreatorUserName(userName);
	}

	public List<Auction> getAllAuctions() {
		return auctionRepository.findAll();
	}

	public List<User> getAuctionEntrants(String auctionName) {
		return findAuctionByName(auctionName).getEntrants();
	}

}
