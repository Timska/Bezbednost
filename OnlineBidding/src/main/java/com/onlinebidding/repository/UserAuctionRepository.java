package com.onlinebidding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.UserAuction;

public interface UserAuctionRepository extends JpaRepository<UserAuction, Long> {

	public List<UserAuction> findByUserUserName(String userName);
	
	public List<UserAuction> findByAuctionAuctionID(Long auctionID);
	
	public UserAuction findByUserUserNameAndAuctionAuctionID(String userName, Long auctionID);
}
