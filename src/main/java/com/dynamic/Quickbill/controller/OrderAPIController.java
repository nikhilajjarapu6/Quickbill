package com.dynamic.Quickbill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class OrderAPIController {

	@RequestMapping("/homepage")
	public String homepage() {
		return "Homepage";
	}
	
	@RequestMapping("/register")
	public String registerPage() {
		return "Register";
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@RequestMapping("/itemsMenu")
	public String itemsMenu() {
		return "ItemsMenu";
	}
	
	@RequestMapping("/finalBill")
	public String finalBill() {
		return "finalBill";
	}
	
	@RequestMapping("/listOfItems")
	public String ItemsList() {
		return "ListOfItems";
	}
	
	@RequestMapping("/addItem")
	public String addItem() {
		return "addItem";
	}
}
