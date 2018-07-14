package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.dao.CustomerDAO;
import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;


@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	
	// need to DI the customer dao
	@Autowired // auto wired will leverage spring to scan and inject the dao.
	private CustomerService customerService;
	
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get customers from the dao
		List<Customer> theCustomers = customerService.getCustomers();
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customerDTO) {
		
		// save the customer using our service
		customerService.saveCustomer(customerDTO);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String updateCustomer(@RequestParam("customerId") int customerId, Model theModel) {
		// get the customer from the service
		Customer theCustomer = customerService.getCustomer(customerId);
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		// send over to our form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int customerId, Model theModel) {
		
		// have the service delete the customer
		customerService.deleteCustomer(customerId);
		return "redirect:/customer/list";
	}
	
	
	@PostMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String searchName, Model theModel) {
		
		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(searchName);
		
		// Bind the customers to the Model.
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
}
