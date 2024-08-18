package test;

import daoImpl.CustomerDaoImpl;
import exception.CustomerNotFound;
import model.Customer;

import java.util.List;

public class CustomerDaoTest {
    private CustomerDaoImpl customerDao;
    public void setUp(){
        customerDao = new CustomerDaoImpl();
    }

//    test--1 get customer bus id
    public void testGetCustomerById() throws CustomerNotFound {
        setUp();
        try {
            Customer customer = customerDao.getCustomerId(1);
            if (customer!=null){
                System.out.println("testGetCustomerById passed: Bus is not null.");
                System.out.println("Bus details: "+customer.getName()+", "+customer.getEmail());
            }else{
                System.out.println("Customer should be not null.");
            }
        }
        catch (CustomerNotFound e) {
            e.getMessage();
            throw new CustomerNotFound("CustomerNotFoundException was thrown.");
        }
    }

//    test--2 add customer
public void testAddCustomer() {
    setUp();
    try {
        Customer newCustomer = new Customer(2, "riya","riya@gmail.com", "9000034312","12345");
        customerDao.addCustomer(newCustomer);
        Customer customer = customerDao.getCustomerId(2);
        if (customer != null && customer.getName().equals("riya")) {
            System.out.println("Customer added successfully.");
        } else {
            System.out.println("Failed to add customer.");
        }
    } catch (Exception e) {
        System.out.println("Exception occurred during adding customer.");
        e.printStackTrace();
    }
}

//    test--3 for updating customer
    public void testUpdateCustomer(){
        setUp();
        try {
            Customer customer = customerDao.getCustomerId(1);
            if (customer == null) {
                System.out.println("Customer with id 1 does not exist.");
                return;
            }
            customer.setName("riti");
            customerDao.updateCustomer(customer);
            Customer updatedCustomer = customerDao.getCustomerId(1);
            if (updatedCustomer!=null &&updatedCustomer.getName().equals("riti") ){
                System.out.println("testUpdateCustomer: passed");
            System.out.println("Customer updated successfully.");
            }else{
                System.out.println("testUpdateCustomer: failed!");
            }
            System.out.println("");
        } catch (CustomerNotFound e) {
            System.out.println("CustomerNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

//    test--4 for deleting customer
    public void testDeleteCustomer(){
        setUp();
        try {
            customerDao.deleteCustomer(3);
            Customer customer = customerDao.getCustomerId(3);
            if (customer==null){
                System.out.println("testDeleteCustomer: passed.");
            }else {
                System.out.println("testDeleteCustomer: failed!");
            }
        } catch (CustomerNotFound e) {
            System.out.println("CustomerNotFoundException was thrown.");
        }
        catch (Exception e) {
            System.out.println("Exception occurred during deleting customer.");
            e.printStackTrace();
        }
    }
    // Test--5 get all customers
    public void testGetAllCustomers() {
        setUp();
        try {
            List<Customer> customers = customerDao.getAllCustomer();
            if (customers != null && !customers.isEmpty()) {
                System.out.println("testGetAllCustomers: passed.");
                for (Customer customer : customers) {
                    System.out.println("Customer: " + customer.getName() + ", " + customer.getEmail());
                }
            } else {
                System.out.println("testGetAllCustomers: failed. No customers found.");
            }
        } catch (CustomerNotFound e) {
            System.out.println("CustomerNotFoundException was thrown.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected exception occurred during testGetAllCustomers.");
            e.printStackTrace();
        }
    }

    // Test--6 get customer by email
    public void testGetCustomerByEmail() {
        setUp();
        try {
            Customer customer = customerDao.getCustomerByEmail("riya@gmail.com");
            if (customer != null) {
                System.out.println("testGetCustomerByEmail: passed.");
                System.out.println("Customer details: " + customer.getName() + ", " + customer.getEmail());
            } else {
                System.out.println("testGetCustomerByEmail: failed. Customer not found.");
            }
        } catch (CustomerNotFound e) {
            System.out.println("CustomerNotFoundException was thrown.");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("An unexpected exception occurred during testGetCustomerByEmail.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws CustomerNotFound {
        CustomerDaoTest test = new CustomerDaoTest();
//        test.testGetCustomerById();
//        test.testAddCustomer();
//        test.testUpdateCustomer();
//        test.testDeleteCustomer();
//        test.testGetAllCustomers();
//        test.testGetCustomerByEmail();
    }
}
