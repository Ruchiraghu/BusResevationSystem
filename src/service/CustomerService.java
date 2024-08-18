package service;

import dao.CustomerDao;
import daoImpl.CustomerDaoImpl;
import exception.CustomerNotFound;
import model.Customer;

import java.util.List;

public class CustomerService {
    private CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerService() {
        this.customerDao = new CustomerDaoImpl();
    }

    // 1. Get customer by ID
    public Customer getCustomerById(int customerId) throws CustomerNotFound {
        Customer customer = customerDao.getCustomerId(customerId);
        if (customer == null) {
            throw new CustomerNotFound("Customer with ID " + customerId + " not found.");
        }
        return customer;
    }

    // 2. Get a list of all customers
    public List<Customer> getAllCustomers() throws CustomerNotFound {
        List<Customer> customers = customerDao.getAllCustomer();
        if (customers.isEmpty()) {
            throw new CustomerNotFound("No customers found.");
        }
        return customers;
    }

    // 3. Add a new customer
    public void addCustomer(Customer customer) throws CustomerNotFound {
        if (customerDao == null) {
            throw new IllegalStateException("CustomerDao is not initialized.");
        }
        customerDao.addCustomer(customer);
    }

    // 4. Update an existing customer's details
    public void updateCustomer(Customer customer) throws CustomerNotFound {
        if (customerDao.getCustomerId(customer.getCustomerId()) == null) {
            throw new CustomerNotFound("Customer with ID " + customer.getCustomerId() + " not found.");
        }
        customerDao.updateCustomer(customer);
    }

    // 5. Delete a customer by their ID
    public void deleteCustomer(int customerId) throws CustomerNotFound {
        if (customerDao.getCustomerId(customerId) == null) {
            throw new CustomerNotFound("Customer with ID " + customerId + " not found.");
        }
        customerDao.deleteCustomer(customerId);
    }

    // 6. Authenticate customer login
    public boolean authenticateCustomer(String email, String password) throws CustomerNotFound {
        Customer customer = customerDao.getCustomerByEmail(email); // Ensure this method exists
        return customer != null && customer.getPassword().equals(password);
    }

    // 7. Change a customer's password
    public void changeCustomerPassword(int customerId, String newPassword) throws CustomerNotFound {
        Customer customer = customerDao.getCustomerId(customerId); // Ensure this method exists
        if (customer != null) {
            customer.setPassword(newPassword);
            customerDao.updateCustomer(customer);
        } else {
            throw new CustomerNotFound("Customer with ID " + customerId + " not found.");
        }
    }
}
