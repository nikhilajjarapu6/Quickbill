package com.dynamic.Quickbill.dao;

import java.util.List;
import java.util.Optional;

import com.dynamic.Quickbill.dto.CustomerOrder;

public interface CustomerDAO {
	public CustomerOrder saveOrder(CustomerOrder order);
	public List<CustomerOrder> list();
	public Optional<CustomerOrder> getOrderById(Integer id);
	public CustomerOrder updateOrder(CustomerOrder order);
	public CustomerOrder deleteOrder(Integer id);
}
