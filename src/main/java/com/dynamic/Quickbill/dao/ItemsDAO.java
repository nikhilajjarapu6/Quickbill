package com.dynamic.Quickbill.dao;

import java.util.List;
import java.util.Optional;

import com.dynamic.Quickbill.dto.Items;

public interface ItemsDAO {
	public Items saveItems(Items items);
	public List<Items> list();
	public Optional<Items> getItemById(Integer id);
	public Items delteItemById(Integer id);
	public Items updateItems(Items items);
	public List<Items> batchSave(List<Items> items);
}
