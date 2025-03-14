package com.dynamic.Quickbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dynamic.Quickbill.dto.CustomerOrder;

public interface CustomerRepository extends JpaRepository<CustomerOrder,Integer> {

}
