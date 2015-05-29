package com.onlinebidding.service;

import java.util.Date;
import java.util.List;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.Item;
import com.onlinebidding.model.User;

public interface AuctionService {
	
	public void create(Auction auction);
	
	public Auction findAuctionByName(String auctionName);
	
	public User getAuctionCreator(String auctionName);
	
	public Item getAuctionItem(String auctionName);
	
	public Date getAuctionStartDate(String auctionName);
	
	public Date getAuctionEndDate(String auctionName);
	
	public List<Auction> getUserAuctions(String userName);
	
	public List<Auction> getAllAuctions();
	
	public List<User> getAuctionEntrants(String auctionName);
}
