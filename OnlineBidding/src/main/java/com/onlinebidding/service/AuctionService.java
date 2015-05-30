package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.Auction;
import com.onlinebidding.model.User;

public interface AuctionService {

	public void create(Auction auction);

	public Auction findAuction(Long auctionID);

	public List<Auction> getAllAuctions();

	public List<Auction> getNotFinishedAuctions();

	public List<Auction> getUserAuctions(String userName);

	public List<Auction> getUserNotFinishedAuctions(String userName);

	public Auction updateAuction(Long auctionID, User user, String price);

	public void enterAuction(Long auctionID, User user);
	
	public List<Auction> getWonUserAuctions(String userName);

	public Auction getAuctionByItemID(Long itemID);
}
