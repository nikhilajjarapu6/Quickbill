package com.dynamic.Quickbill.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dynamic.Quickbill.dto.Items;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.service.ItemsSerivice;

@RestController
@RequestMapping("/api")
public class ItemsController {
	@Autowired
	ItemsSerivice service;
	
	@PostMapping("/items")
	public ResponseEntity<Items> saveItems(@RequestBody Items item) {
		Items item2=service.saveItems(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(item2);
	}
	
	@PostMapping("/items/batch")
	public ResponseEntity<List<Items>> BatchSave(@RequestBody List<Items> items){
		List<Items> list=service.batchSaving(items);
		return ResponseEntity.status(HttpStatus.CREATED).body(list);
	}
	
	@GetMapping("/items")
	public ResponseEntity<List<Items>> listItems(){
		List<Items> list=service.listOfItems();
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("No items available in the inventory.");
		}
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/items/{id}")
	public ResponseEntity<Items> getById(@PathVariable Integer id) {
	    Optional<Items> item = service.getItemById(id);
	    return item.map(ResponseEntity::ok)  // If item is found, return 200 OK with the item
	               .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());  // If not found, return 404
	}

	 
	@DeleteMapping("/items/{id}")
	public ResponseEntity<Items> deleteItemById(@PathVariable Integer id) {
		service.removeItemById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/items")
	public ResponseEntity<Items> updateItem(@RequestBody Items item) {
		Items item2=service.UpdateItem(item);
		return ResponseEntity.ok(item2);
	}
	
	
}
