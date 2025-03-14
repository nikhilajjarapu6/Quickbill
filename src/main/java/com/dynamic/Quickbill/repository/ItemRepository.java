package com.dynamic.Quickbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dynamic.Quickbill.dto.Items;

public interface ItemRepository extends JpaRepository<Items,Integer> {

}
