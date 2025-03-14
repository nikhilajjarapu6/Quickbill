package com.dynamic.Quickbill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dynamic.Quickbill.dto.Biller;

public interface BillerRepository extends JpaRepository<Biller, Integer> {

    @Query("SELECT a FROM Biller a WHERE a.email = :email AND a.password = :password")
    public Biller billerValidation(@Param("email") String username, @Param("password") String password);
}
