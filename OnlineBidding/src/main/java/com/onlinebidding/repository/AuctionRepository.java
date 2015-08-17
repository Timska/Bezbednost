package com.onlinebidding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

	public List<Auction> findByCreatorUserName(String userName);
	
	public List<Auction> findByWinnerUserName(String userName);
	
	public Auction findByItemItemID(Long itemID);
	
	public void delete(Long auctionID);
	
}
