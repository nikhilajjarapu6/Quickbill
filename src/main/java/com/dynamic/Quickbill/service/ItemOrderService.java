package com.dynamic.Quickbill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamic.Quickbill.dao.CustomerDAO;
import com.dynamic.Quickbill.dao.ItemsDAO;
import com.dynamic.Quickbill.dao.OrderItemDao;
import com.dynamic.Quickbill.dto.CustomerOrder;
import com.dynamic.Quickbill.dto.ItemOrder;
import com.dynamic.Quickbill.dto.Items;
import com.dynamic.Quickbill.exceptions.BadrequestException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;

@Service
public class ItemOrderService {

	@Autowired
	private OrderItemDao dao;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private ItemsDAO itemsDAO;
	
	
	public ItemOrder saveItemOrder(ItemOrder order) {
		//validating order
		validateItemOrder(order);
		//retrieving customer order and items
		CustomerOrder customerOrder=retrieveCustomer(order);
		Items items=retrieveItem(order);
		
		//initializing order
		order.setCustomerOrder(customerOrder);
		order.setItem(items);
		return dao.saveOrderItem(order);
	}
	
	public List<ItemOrder> listOfItemOrder() {
		return dao.listOfOrderItems();
	}
	
	public Optional<ItemOrder> getItemOrderById(Integer id) {
		//fetching order based on id
		return Optional.ofNullable(dao.getOrderItemById(id).orElseThrow(()->
				new ResourceNotFoundException("order with id "+id+"not found"))); 
	}
	
	public ItemOrder removeItemOrder(Integer id) {
		//fetching order based on id
		dao.getOrderItemById(id)
        	.orElseThrow(() -> new ResourceNotFoundException("Order with ID " + id + " not found"));
		return dao.removeOrderItem(id);
	}
	
	public ItemOrder updateItemOrder(ItemOrder order) {
		//validating order
		validateItemOrder(order);
		
		//retrieving the order
		ItemOrder existingOrder=dao.getOrderItemById(order.getOrderId()).orElseThrow(()->
				new ResourceNotFoundException("order with id "+order.getOrderId()+"not found"));
		
		// retrieving ordere and items
		CustomerOrder customerOrder=retrieveCustomer(order);
		Items items=retrieveItem(order);
		
		//updateing existing order
		existingOrder.setCustomerOrder(customerOrder);
		existingOrder.setItem(items);
		existingOrder.setPrice(order.getPrice());
		existingOrder.setQuantity(order.getQuantity());
		return dao.updateItemOrder(existingOrder);
	}
	
	public void validateItemOrder(ItemOrder itemOrder) {
		//order validating
	    if (itemOrder == null) {
	        throw new ResourceNotFoundException("Item order not found");
	    }
	    
	    if (itemOrder.getCustomerOrder() == null) {
	        throw new ResourceNotFoundException("Customer order is required");
	    }
	    
	    if (itemOrder.getItem() == null) {
	        throw new ResourceNotFoundException("Item is required");
	    }
	    
	    if (itemOrder.getPrice() == null || itemOrder.getPrice() <= 0) {
	        throw new BadrequestException("Valid price is required");
	    }
	    
	    if (itemOrder.getQuantity() == null || itemOrder.getQuantity() <= 0) {
	        throw new BadrequestException("Valid quantity is required");
	    }
	}
	
	//helper method for fetching customer
	public CustomerOrder retrieveCustomer(ItemOrder order) {
	    if (order.getCustomerOrder() == null) {
	        throw new ResourceNotFoundException("Customer order is not provided in ItemOrder.");
	    }
	    return customerDAO.getOrderById(order.getCustomerOrder().getCustomer_Id())
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Customer order with ID " + order.getCustomerOrder().getCustomer_Id() + " not found"));
	}

	
	//helper method for fetching item
	public Items retrieveItem(ItemOrder order) {
	    if (order.getItem() == null) {
	        throw new ResourceNotFoundException("Item is not provided in ItemOrder.");
	    }
	    return itemsDAO.getItemById(order.getItem().getItemId())
	             .orElseThrow(() -> new ResourceNotFoundException(
	                     "Item with ID " + order.getItem().getItemId() + " not found"));
	}

}
