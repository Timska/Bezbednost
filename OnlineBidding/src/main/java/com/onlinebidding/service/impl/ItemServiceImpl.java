package com.onlinebidding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinebidding.model.Item;
import com.onlinebidding.repository.ItemRepository;
import com.onlinebidding.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public void create(Item item) {
		itemRepository.save(item);
	}

	public Item findItem(Long itemID) {
		return itemRepository.findOne(itemID);
	}

	public List<Item> getAllItems() {
		return itemRepository.findAll();
	}

	public void delete(Long itemID) {
		itemRepository.delete(itemID);
	}

}
