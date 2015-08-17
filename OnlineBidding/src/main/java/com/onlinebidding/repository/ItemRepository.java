package com.onlinebidding.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlinebidding.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	public void delete(Long itemID);
}
