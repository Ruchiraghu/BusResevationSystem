package dao;

import exception.CustomerNotFound;
import model.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomerId(int customerId) throws CustomerNotFound;
    List<Customer> getAllCustomer() throws CustomerNotFound;
    void updateCustomer(Customer customer) throws CustomerNotFound;
    void deleteCustomer(int customerId) throws CustomerNotFound;
    Customer getCustomerByEmail(String email) throws CustomerNotFound;

    void addCustomer(Customer customer) throws CustomerNotFound;
}
