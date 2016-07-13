package tap.exampleapp.logic;

import java.util.ArrayList;
import java.util.List;

public class BusinessLogic {
	
	public void saveCustomer(Customer c) {
		
	}
	
	public Customer getCustomer(int id) {
		Customer c = new Customer();
		
		c.setFirstName("John");
		c.setLastName("Connor");
		c.setIdCustomer(id);
		
		return c;
	}
	
	public List<Customer> listCustomer() {
		List<Customer> customers = new ArrayList<Customer>();
		
		Customer c = new Customer();
		c.setFirstName("Bart");
		c.setLastName("Simpson");
		c.setIdCustomer(25);
		
		customers.add(c);
		
		c = new Customer();
		c.setFirstName("Marge");
		c.setLastName("Simpson");
		c.setIdCustomer(25);
		
		customers.add(c);
		
		return customers;
	}
}
