package com.dynamic.Quickbill.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamic.Quickbill.dao.ItemsDAO;
import com.dynamic.Quickbill.dto.Items;
import com.dynamic.Quickbill.exceptions.BadrequestException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;

@Service
public class ItemsSerivice {
	@Autowired
	ItemsDAO dao;
	
	public Items saveItems(Items items) {
		//validating items
		validateItems(items);
		return dao.saveItems(items);
	}
	
	public List<Items> listOfItems(){
		return dao.list();
	}
	
	public Optional<Items> getItemById(Integer id) {
		//fetching item based on id
		return Optional.ofNullable(dao.getItemById(id).orElseThrow(()->
					new ResourceNotFoundException("Item with id "+id+" not found")));
	}
	
	public Items removeItemById(Integer id) {
		//checking item with id is present or not
		dao.getItemById(id).orElseThrow(()->
			new ResourceNotFoundException("Item with id "+id+" not found"));
		return dao.delteItemById(id);
	}
	
	
	public Items UpdateItem(Items items) {
		//validating items
		validateItems(items);
		
		//fetching items
		Items existingItem=dao.getItemById(items.getItemId()).orElseThrow(()->
				new ResourceNotFoundException("Item with id "+items.getItemId()+" not found"));
		
		//updating item 
		existingItem.setItemName(items.getItemName());
		existingItem.setPrice(items.getPrice());
		return dao.updateItems(existingItem);
	}

	public List<Items> batchSaving(List<Items> items) {
	    List<Items> validItems = new ArrayList<>();
	    List<String> validationErrors = new ArrayList<>();
	    
	    // Validating every item
	    for (Items item : items) {
	        try {
	            validateItems(item);
	            validItems.add(item);
	        } catch (BadrequestException e) {
	            validationErrors.add("Item with ID " + item.getItemId() + " failed validation: " + e.getMessage());
	        }
	    }
	    
	    if (!validItems.isEmpty()) {
	        dao.batchSave(validItems);
	    }
	    
	    // Optionally, you can log or return the validation errors
	    if (!validationErrors.isEmpty()) {
	        System.out.println("Validation errors: " + String.join(", ", validationErrors));
	    }
	    
	    return validItems; // Return the successfully saved items
	}

	
	public void validateItems(Items item) {
		if(item==null) {
			throw new ResourceNotFoundException("Items are not found");
		}
		if(item.getItemName()==null) {
			throw new BadrequestException("item name is required");
		}
		if(item.getPrice()==null || item.getPrice()<=0) {
			throw new BadrequestException("item price is required");
		}
	}
}
