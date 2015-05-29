package com.onlinebidding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, String> {

	public List<Auction> findByCreatorUserName(String userName);
}
