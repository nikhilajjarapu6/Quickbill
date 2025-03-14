package com.dynamic.Quickbill.dao;

import java.util.List;
import java.util.Optional;

import com.dynamic.Quickbill.dto.ItemOrder;

public interface OrderItemDao {
	public ItemOrder saveOrderItem(ItemOrder order);
	public List<ItemOrder> listOfOrderItems();
	public Optional<ItemOrder> getOrderItemById(Integer id);
	public ItemOrder removeOrderItem(Integer id);
	public ItemOrder updateItemOrder(ItemOrder order);
}
