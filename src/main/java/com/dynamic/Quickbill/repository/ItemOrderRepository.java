package com.dynamic.Quickbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dynamic.Quickbill.dto.ItemOrder;

public interface ItemOrderRepository extends JpaRepository<ItemOrder,Integer>{

}
