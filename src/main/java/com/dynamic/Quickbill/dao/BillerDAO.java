package com.dynamic.Quickbill.dao;



import java.util.List;
import java.util.Optional;

import com.dynamic.Quickbill.dto.Biller;

public interface BillerDAO {
	public Biller saveBiller(Biller biller);
	public List<Biller> listOfBillers();
	public Optional<Biller> getById(Integer id);
	public Biller removeBiller(Integer id);
	public Biller updateBiller(Biller biller);
	public Biller validation(String username,String password);
}
