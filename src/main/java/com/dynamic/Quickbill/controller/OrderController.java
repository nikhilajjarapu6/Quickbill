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

import com.dynamic.Quickbill.dto.CustomerOrder;
import com.dynamic.Quickbill.dto.ItemOrder;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.service.CustomerOrderService;
import com.dynamic.Quickbill.service.ItemOrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	CustomerOrderService service;
	
	@Autowired
	ItemOrderService orderService;
	
	@PostMapping("/orders")
	public ResponseEntity<CustomerOrder> saveOrder(@RequestBody CustomerOrder order) {
	    CustomerOrder savedOrder = service.saveOrder(order);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}

	
	@GetMapping("/orders")
	public ResponseEntity<List<CustomerOrder>> listOfOrders(){
		List<CustomerOrder> list=service.list();
		if(list.isEmpty()) {
			throw new ResourceNotFoundException("No orders found");
		}
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/orders/{id}")
	public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Integer id) {
		CustomerOrder order=service.getOrderById(id);
		return ResponseEntity.ok(order);
	}
	
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<CustomerOrder>  deleteOrder(@PathVariable Integer id) {
		service.deleteOrder(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/orders")
	public ResponseEntity<CustomerOrder>  updateOrder(@RequestBody CustomerOrder order) {
		CustomerOrder order2 =service.updateOrder(order);
		return ResponseEntity.ok(order2);
	}
	
	@GetMapping("/itemOrder/{id}")
	public ResponseEntity<ItemOrder>  getOrderDetailes(Integer id) {
		 Optional<ItemOrder> itemOrderOptional = orderService.getItemOrderById(id);
		    return itemOrderOptional.map(ResponseEntity::ok)
		                            .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
		}
		
	

}
