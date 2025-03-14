package com.dynamic.Quickbill.daoIMPL;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dynamic.Quickbill.dao.BillerDAO;
import com.dynamic.Quickbill.dto.Biller;
import com.dynamic.Quickbill.exceptions.BillerAuthenticationException;
import com.dynamic.Quickbill.exceptions.ResourceNotFoundException;
import com.dynamic.Quickbill.repository.BillerRepository;

@Repository
public class BillerDAOImpl implements BillerDAO {
    
    @Autowired
    private BillerRepository repository;

    @Override
    public Biller saveBiller(Biller biller) {
        return repository.save(biller);
    }
    
    @Override
	public List<Biller> listOfBillers() {
		return repository.findAll();
	}

    @Override
    public Optional<Biller> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Biller removeBiller(Integer id) {
        	Biller biller = repository.findById(id).orElseThrow(()->
        	new ResourceNotFoundException("Biller with ID " + id + " not found"));
        repository.deleteById(id);
        return biller;
       
    }

    @Override
    public Biller updateBiller(Biller biller) {
        if (repository.existsById(biller.getBillerId())) {
            return repository.save(biller); // Update the existing Biller
        }
        throw new ResourceNotFoundException("Biller with ID " + biller.getBillerId() + " not found");
    }

    @Override
    public Biller validation(String username, String password){
     
        Biller biller = repository.billerValidation(username, password);
        if (biller != null) {
            return biller;
        } 
        
        throw new BillerAuthenticationException("No matching biller found");
    }


	
}
