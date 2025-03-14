package com.dynamic.Quickbill.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dynamic.Quickbill.dao.BillerDAO;
import com.dynamic.Quickbill.dto.Biller;
import com.dynamic.Quickbill.exceptions.BadrequestException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



@Service
public class BillerService {
	@Autowired
	BillerDAO dao;
	
	@Autowired
	HttpServletRequest request;
	
	public Biller saveBiller(Biller biler) {
		billerValidation(biler);
		return dao.saveBiller(biler);
	}
	
	public List<Biller> listOfBiller(){
		return dao.listOfBillers();
	}
	
	public Optional<Biller> getBillerById(Integer id) {
		 return Optional.ofNullable(dao.getById(id)
	              .orElseThrow(() -> new ResourceNotFoundException("Biller with id " + id + " not found")));
	}
	
	public Biller deleteBillerById(Integer id) {
		Optional<Biller> biller=Optional.ofNullable(dao.getById(id)).orElseThrow(()->
				new ResourceNotFoundException("Failed to find biller with id "+id));
		dao.removeBiller(id);
		return biller.get();
		
	}
	
	public Biller updateBiller(Biller biller) {
		Biller existingBiller = dao.getById(biller.getBillerId())
	            .orElseThrow(() -> new ResourceNotFoundException("Biller with ID " + biller.getBillerId() + " not found"));
		
		
		billerValidation(existingBiller);
		existingBiller.setEmail(biller.getEmail());
		existingBiller.setPassword(biller.getPassword());
		
		return dao.updateBiller(existingBiller);
	}
	
	public Biller validation(String username,String password) {
		 Biller biller = dao.validation(username, password);
		    if (biller == null) {
		        throw new BadrequestException("Invalid username or password");
		    }
		    HttpSession session = request.getSession();
		    session.setAttribute("Biller", biller);
		    return biller;
	}
	
	public String logOut() {
		HttpSession session=request.getSession();
		if(session!=null) {
			session.invalidate();
		}
		return "Log out Sucessfully";
	}
	
	public void billerValidation(Biller biller) {
		 if (biller == null) {
		        throw new BadrequestException("Biller object cannot be null");
		    }
		    if (biller.getEmail() == null || biller.getEmail().isEmpty()) {
		        throw new BadrequestException("Email is required");
		    }
		    if (biller.getPassword() == null || biller.getPassword().isEmpty()) {
		        throw new BadrequestException("Password is required");
		    }
	}
}
