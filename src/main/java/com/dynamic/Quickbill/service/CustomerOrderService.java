package com.dynamic.Quickbill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dynamic.Quickbill.dao.CustomerDAO;
import com.dynamic.Quickbill.dao.ItemsDAO;
import com.dynamic.Quickbill.dao.OrderItemDao;
import com.dynamic.Quickbill.dto.Biller;
import com.dynamic.Quickbill.dto.CustomerOrder;
import com.dynamic.Quickbill.dto.ItemOrder;
import com.dynamic.Quickbill.dto.Items;
import com.dynamic.Quickbill.exceptions.BadrequestException;
import com.dynamic.Quickbill.exceptions.BillerAuthenticationException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CustomerOrderService {

	@Autowired
	private CustomerDAO dao;
	
	@Autowired
	private OrderItemDao itemDao;
	
	@Autowired
	private ItemsDAO dao2;
	
	@Autowired
	HttpServletRequest request;

	public CustomerOrder saveOrder(CustomerOrder order) {
	    HttpSession session = request.getSession();
	    Biller biller = validateBillerSession(session);
        	validateOrders(order);
	        CustomerOrder savedOrder = dao.saveOrder(order);
	        savedOrder.setBillerId(biller);
	        savedOrder.setTotal_amount(order.getTotal_amount());

	        
	        if (savedOrder != null) {
	        	validateItemOrders(order.getItemOrders());
	            for (ItemOrder itemOrder : order.getItemOrders()) {
	                itemOrder.setCustomerOrder(savedOrder);
	                Items item = retriveItem(itemOrder);
	                itemOrder.setItem(item);
                    itemDao.saveOrderItem(itemOrder);
	            }
	        }
	    
	        return savedOrder;
	}


	public List<CustomerOrder> list() {
		return dao.list();
	}

	   public CustomerOrder getOrderById(Integer id) {
	        return dao.getOrderById(id)
	                  .orElseThrow(() -> new ResourceNotFoundException("Customer Order with ID " + id + " not found"));
	    }
	
	public CustomerOrder deleteOrder(Integer id) {
		return Optional.ofNullable(dao.deleteOrder(id)).orElseThrow(()->
				new ResourceNotFoundException("Failed to customer order with ID " + id));
		
	}

	public CustomerOrder updateOrder(CustomerOrder order) {
		  validateOrders(order);
		  CustomerOrder existingOrder = retirveCustomerOrder(order);
	       
		  //updating customerId and total amount
	        existingOrder.setCustomer_Id(order.getCustomer_Id());
	        existingOrder.setTotal_amount(order.getTotal_amount());
	        if (order.getItemOrders() != null) {
	            for (ItemOrder itemOrder : order.getItemOrders()) {
	            	validateItemOrders(order.getItemOrders());
	                Items item = retriveItem(itemOrder);
	                    itemOrder.setCustomerOrder(existingOrder);
	                    itemOrder.setItem(item);
	                    itemDao.saveOrderItem(itemOrder);
	            }
	        }
//	        validateOrders(existingOrder);
	        return dao.saveOrder(existingOrder);
	    }
	
	public Biller validateBillerSession(HttpSession session) {
	    Biller biller = (Biller) session.getAttribute("Biller");
	    if (biller == null) {
	        throw new BillerAuthenticationException("Biller is not authenticated. Please log in first.");
	    }
	    return biller;
	}

	public void validateItemOrders(List<ItemOrder> itemOrders) {
	    for (ItemOrder itemOrder : itemOrders) {
	    	 if (itemOrder == null) {
	             throw new BadrequestException("Item order cannot be null.");
	         }

	         if (itemOrder.getItem() == null) {
	             throw new BadrequestException("Item cannot be null in item order.");
	         }

	         if (itemOrder.getItem().getItemId() == null) {
	             throw new BadrequestException("Item ID is required in item order.");
	         }

	         if (itemOrder.getQuantity() == null || itemOrder.getQuantity() <= 0) {
	             throw new BadrequestException("Quantity must be greater than zero in item order.");
	         }

	         if (itemOrder.getPrice() == null || itemOrder.getPrice() <= 0) {
	             throw new BadrequestException("Price must be greater than zero in item order.");
	         }
	        dao2.getItemById(itemOrder.getItem().getItemId())
	            .orElseThrow(() -> new ResourceNotFoundException("Item with ID " + itemOrder.getItem().getItemId() + " not found"));
	    }
	}
	
	public CustomerOrder retirveCustomerOrder(CustomerOrder order) {
		return dao.getOrderById(order.getCustomer_Id())
		        .orElseThrow(() -> new ResourceNotFoundException("Customer Order with ID " + order.getCustomer_Id() + " not found"));
		
	}
	
	public Items retriveItem(ItemOrder itemOrder) {
		return dao2.getItemById(itemOrder.getItem().getItemId())
       		 .orElseThrow(() -> new ResourceNotFoundException("Item with ID " + itemOrder.getItem().getItemId() + " not found"));
		
	}
	
	public void validateOrders(CustomerOrder order) {
		if(order.getTotal_amount()==null || order.getTotal_amount()<=0) {
			throw new BadrequestException("amount is requried");
		}
//		if(order.getBillerId()==null) {
//			throw new BadrequestException("biller  is requried");
//		}
		if(order.getItemOrders()==null) {
			throw new BadrequestException("item id is requried");
		}
//		if(order.getCustomer_Id()==null) {
//			throw new BadrequestException("customer id is required");
//		}
	}

}
