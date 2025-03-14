package com.dynamic.Quickbill.daoIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dynamic.Quickbill.dao.CustomerDAO;
import com.dynamic.Quickbill.dto.CustomerOrder;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.repository.CustomerRepository;

@Repository
public class CustomerOrderDAOImpl implements CustomerDAO {
	@Autowired
	CustomerRepository repository;

	@Override
	public CustomerOrder saveOrder(CustomerOrder order) {
		return repository.save(order);
	}

	@Override
	public List<CustomerOrder> list() {
		return repository.findAll();
	}

	@Override
	public Optional<CustomerOrder>  getOrderById(Integer id) {
	    return repository.findById(id);
	}

	@Override
	public CustomerOrder updateOrder(CustomerOrder order) {
	    if (repository.existsById(order.getCustomer_Id())) {
	        return repository.save(order); 
	    }
	    throw new ResourceNotFoundException("Customer Order With Id "+order.getCustomer_Id()+" not found");
	}

	@Override
	public CustomerOrder deleteOrder(Integer id) {
		CustomerOrder order=repository.findById(id).orElseThrow(()->
			new ResourceNotFoundException("Customer Order With Id "+id+" not found"));
			repository.deleteById(id);
			return order;
	}


	
	
}
