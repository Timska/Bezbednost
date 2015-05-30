package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.Auction;

public interface AuctionService {
	
	public void create(Auction auction);
	
	public void updateAuction(Long auctionID, String price);
	
	public Auction findAuction(Long auctionID);
	
	public List<Auction> getAllAuctions();
	
	public List<Auction> getNotFinishedAuctions();
	
	public List<Auction> getUserAuctions(String userName);
	
	public List<Auction> getUserNotFinishedAuctions(String userName);
	
	public Auction getAuctionByItemID(Long itemID);
}
