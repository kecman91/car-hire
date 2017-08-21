package beans;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import model.Customer;

@Local
public interface CustomerBean {
	List<Customer> loadAllCustomers();
	Customer saveCustomer(Customer c);
	boolean deleteCustomer(int id);
	Customer loadCustomer(int id);
	List<Customer> getByUnpaidBookings();
	List<Customer> search(String searchTerm);
}
