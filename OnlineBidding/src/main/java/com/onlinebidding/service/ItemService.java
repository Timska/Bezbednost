package com.onlinebidding.service;

import java.util.List;

import com.onlinebidding.model.Item;

public interface ItemService {

	public void create(Item item);
	
	public Item findItem(Long itemID);
	
	public List<Item> getAllItems();
	
	public void delete(Long itemID);
}
