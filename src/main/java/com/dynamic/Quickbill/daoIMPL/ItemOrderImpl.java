package com.dynamic.Quickbill.daoIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dynamic.Quickbill.dao.OrderItemDao;
import com.dynamic.Quickbill.dto.ItemOrder;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.repository.ItemOrderRepository;

@Repository
public class ItemOrderImpl implements OrderItemDao {

	@Autowired
	private ItemOrderRepository repository;

	@Override
	public ItemOrder saveOrderItem(ItemOrder order) {
		return repository.save(order);
	}

	@Override
	public List<ItemOrder> listOfOrderItems() {
		return repository.findAll();
	}

	@Override
	public Optional<ItemOrder>  getOrderItemById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public ItemOrder removeOrderItem(Integer id) {
		ItemOrder itemOrder = getOrderItemById(id).orElseThrow(()->
		 new ResourceNotFoundException("Order with id "+id+" not found"));
			repository.deleteById(id);
			return itemOrder;
	}

	@Override
	public ItemOrder updateItemOrder(ItemOrder order) {
		if (repository.existsById(order.getOrderId())) {
			return repository.save(order);
		}
		throw new ResourceNotFoundException("Order with id "+order.getOrderId()+" not found");
	}
}
