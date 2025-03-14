package com.dynamic.Quickbill.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class CustomerOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customer_Id;
	
	@Column
	@JsonProperty("total_amount")
	private Double total_amount;
	
	@Column(name="Date")
	private Timestamp date;
	
	@OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ItemOrder> itemOrders;
	
	@ManyToOne
	@JoinColumn(name = "biller_id")
	private Biller billerId;


	
	public Integer getCustomer_Id() {
		return customer_Id;
	}


	public void setCustomer_Id(Integer customer_Id) {
		this.customer_Id = customer_Id;
	}


	public Double getTotal_amount() {
		return total_amount;
	}


	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}


	public List<ItemOrder> getItemOrders() {
		return itemOrders;
	}


	public void setItemOrders(List<ItemOrder> itemOrders) {
		this.itemOrders = itemOrders;
	}


	public Timestamp getDate() {
		return date;
	}


	public void setDate(Timestamp date) {
		this.date = date;
	}


	public Biller getBillerId() {
		return billerId;
	}


	public void setBillerId(Biller billerId) {
		this.billerId = billerId;
	}
	
	@PrePersist
	public void prePersist(){
		if(date==null) {
			date = new Timestamp(System.currentTimeMillis());
		}
	}
	
	
	
	
	
	
}
