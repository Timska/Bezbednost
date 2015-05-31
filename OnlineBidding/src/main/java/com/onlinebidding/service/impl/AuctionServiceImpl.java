package com.onlinebidding.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Auction;
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
	
	public Auction updateAuction(Long auctionID, User user, String price) {
		Auction auction = auctionRepository.findOne(auctionID);
		auction.setCurrentPrice(price);
		auction.setWinner(user);
		return auctionRepository.save(auction);
	}
	
	public Auction findAuction(Long auctionID) {
		return auctionRepository.findOne(auctionID);
	}
	
	public List<Auction> getAllAuctions() {
		return auctionRepository.findAll();
	}
	
	public List<Auction> getNotFinishedAuctions() {
		List<Auction> auctions = getAllAuctions();
		Date currentDate = new Date();
		System.err.println(currentDate.toString());
		for (int i = auctions.size() - 1; i >= 0; i--) {
			Auction auction = auctions.get(i);
			System.err.println(auction.getAuctionName()+" "+auction.getEndDate().toString());
			if (currentDate.after(auction.getEndDate())) {
				auctions.remove(auction);
			}
		}
		return auctions;
	}

	public List<Auction> getUserAuctions(String userName) {
		return auctionRepository.findByCreatorUserName(userName);
	}
	
	public List<Auction> getUserNotFinishedAuctions(String userName) {
		List<Auction> auctions = getUserAuctions(userName);
		Date currentDate = new Date();
		for (int i = auctions.size() - 1; i >= 0; i--) {
			Auction auction = auctions.get(i);
			if (currentDate.after(auction.getEndDate())) {
				auctions.remove(auction);
			}
		}
		return auctions;
	}
	
	public Auction getAuctionByItemID(Long itemID) {
		return auctionRepository.findByItemItemID(itemID);
	}

	public List<Auction> getWonUserAuctions(String userName) {
		return auctionRepository.findByWinnerUserName(userName);
	}
}





