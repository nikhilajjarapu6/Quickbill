package com.dynamic.Quickbill.daoIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dynamic.Quickbill.dao.ItemsDAO;
import com.dynamic.Quickbill.dto.Items;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.repository.ItemRepository;

@Repository
public class ItemsDAOImpl implements ItemsDAO {
	
	@Autowired
	ItemRepository repository;

	@Override
	public Items saveItems(Items items) {
		return repository.save(items) ;
	}

	@Override
	public List<Items> list() {
		return repository.findAll();
	}

	@Override
	public Optional<Items>  getItemById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Items delteItemById(Integer id) {
		Items items=repository.findById(id).orElseThrow(()->
			new ResourceNotFoundException("Item with id "+id+" not found"));
			repository.delete(items);
			return items;
	}

	@Override
	public Items updateItems(Items items) {
		if(repository.existsById(items.getItemId())){
			return repository.save(items);
		}
		throw new ResourceNotFoundException("item with id "+items.getItemId()+" not found");
	}

	@Override
	public List<Items> batchSave(List<Items> items) {
		return repository.saveAll(items);
	}

}
