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

import com.dynamic.Quickbill.dto.Biller;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.service.BillerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class BillerController {
	
	@Autowired
	BillerService service;
	
	@Autowired
	HttpServletRequest request;
	
	@PostMapping("/auth")
	public ResponseEntity<Biller>  registerBiller(@RequestBody Biller biller) {
		Biller biller2=service.saveBiller(biller);
		return ResponseEntity.status(HttpStatus.CREATED).body(biller2);
	}
	
	@GetMapping("/auth")
	public ResponseEntity<List<Biller>> listOfBiller() {
	    List<Biller> billers = service.listOfBiller();
	    if (billers == null || billers.isEmpty()) {
	        throw new ResourceNotFoundException("No Billers found.");
	    }
	    return ResponseEntity.ok(billers);
	}

	
	@GetMapping("/auth/{id}")
	public ResponseEntity<Optional<Biller>> getBillerById(@PathVariable Integer id) {
		Optional<Biller> biller=service.getBillerById(id);
		return  ResponseEntity.ok(biller);
	}
	
	@DeleteMapping("/auth/{id}")
	public ResponseEntity<Biller> removeBillerById(@PathVariable Integer id) {
		service.deleteBillerById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/auth")
	public ResponseEntity<Biller> updateBiller(@RequestBody Biller biller) {
		Biller biller2=service.updateBiller(biller);
		return ResponseEntity.ok(biller2);
	}
	
	@PostMapping("/auth/{email}/{password}")
	public ResponseEntity<Biller> login(@PathVariable String email, @PathVariable String password) {
		Biller biller= service.validation(email, password);
		HttpSession session = request.getSession();
	    session.setAttribute("Biller", biller);
	    return ResponseEntity.ok(biller);
//		return ResponseEntity.ok(biller);
	}
	
	@GetMapping("/auth/logout")
	public ResponseEntity<String> logOut() {
		HttpSession session=request.getSession();
		Biller biller=(Biller)session.getAttribute("Biller");
		System.out.println(biller.getEmail());
		if(session!=null) {
			session.removeAttribute("Biller");
		}
		return ResponseEntity.ok("Logged out successfully!");
	}
	
}

