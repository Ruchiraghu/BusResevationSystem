package test;

import daoImpl.CustomerDaoImpl;
import exception.CustomerNotFound;
import model.Customer;
import service.CustomerService;

import java.util.List;

public class CustomerServiceTest {
    private CustomerService customerService;
    private CustomerDaoImpl customerDao;

    public void setUp(){
        customerDao = new CustomerDaoImpl();
        customerService = new CustomerService(customerDao);
    }

//    test--1 adding customer
    public void testAddCustomer(){
        setUp();
        try {
            Customer newCustomer = new Customer(3,"Ruchi","ruchi25@gmail.com","9926767551","Ruchi123");
            customerService.addCustomer(newCustomer);
            Customer customer = customerService.getCustomerById(3);
            if (customer!=null&&customer.getName().equals("Ruchi")){
                System.out.println("testAddCustomer: passes");
//                System.out.println("Customer added successfully!");
            }else{
                System.out.println("testAddCustomer: failed! ");
                System.out.println("Try again!");
            }
        }catch (Exception e){
            System.out.println("Exception occurred during adding customer.");
            e.printStackTrace();
        }
    }

    //        Test--2 get customer by id
    public void testGetCustomerById(){
        setUp();
        try{
            Customer customer = customerService.getCustomerById(1);
            if (customer!=null){
                System.out.println("testGetCustomerById: passed.");
                System.out.println("Customer details: "+customer.getName()+", "+customer.getEmail());
            }
        else {
                System.out.println("testGetCustomerById: failed!");
                System.out.println("Try again getting details");
        }
        }catch (Exception e){
            System.out.println("Exception occurred during getting customer by Id.");
            e.printStackTrace();
        }
    }

//    Test--3 update customer
    public void testUpdateCustomer(){
        setUp();
        try{
            Customer customer = customerService.getCustomerById(1);
            if (customer!=null){
                customer.setName("pooja");
                customerService.updateCustomer(customer);
                Customer updatedCustomer = customerService.getCustomerById(1);
                if (updatedCustomer!=null && updatedCustomer.getName().equals("pooja")){
                    System.out.println("testUpdateCustomer: passed");
                    System.out.println("Customer updated successfully!");
                }
            else{
                    System.out.println("testUpdateCustomer: failed!");
                    System.out.println("Try again to update details of customer!");
            }
            }
        }catch (Exception e){
            System.out.println("Exception occurred during updating customer.");
            e.printStackTrace();
        }    }

//    Test-- 4 delete customer
    public void testDeleteCustomer(){
        setUp();
        try{     customerService.deleteCustomer(3);
            // Check if customer is deleted
            try {
                Customer customer = customerService.getCustomerById(3);
                if (customer == null) {
                    System.out.println("testDeleteCustomer: passed! Customer deleted successfully.");
                } else {
                    System.out.println("testDeleteCustomer: failed! Customer still exists.");
                }
            } catch (CustomerNotFound e) {
                // Expected, means deletion was successful
                System.out.println("testDeleteCustomer: passed! Customer deleted successfully.");
            }
        }catch (Exception e){
            System.out.println("Exception occurred while deleting customer!");
            e.printStackTrace();
        }
    }

//    Test--5 authentication of customer
    public void testAuthenticateCustomer(){
        setUp();
        try {
            boolean isAuthenticated = customerService.authenticateCustomer("ruchi25@gmail.com","Ruchi123");
            if (isAuthenticated){
                System.out.println("testAuthenticateCustomer: passed");
            }else{
                System.out.println("testAuthenticateCustomer: failed!");
            }
        }catch (Exception e){
            System.out.println("Exception occurred while authenticating customer.");
            e.printStackTrace();
        }    }

//    test--6 for changing user password
    public void testCustomerPassword(){
        setUp();
        try{
            customerService.changeCustomerPassword(3,"Ruchi");
            Customer customer = customerService.getCustomerById(3);
            if (customer!=null&&customer.getPassword().equals("Ruchi")){
                System.out.println("testCustomerPassword: passed");
                System.out.println("Password changed successfully.");
            }  else{
                System.out.println("testCustomerPassword: failed!");
            }
        }catch (Exception e){
            System.out.println("CustomerNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

//    Test--7 get all customer
    public void testGetAllCustomers() {
        setUp();
        try {
            List<Customer> customers = customerService.getAllCustomers();
            if (customers != null && !customers.isEmpty()) {
                System.out.println("testGetAllCustomers: passed");
                for (Customer customer : customers) {
                    System.out.println("Customer: " + customer.getName() + ", " + customer.getEmail());
                }
            } else {
                System.out.println("testGetAllCustomers: failed! No customers found.");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred during getting all customers.");
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        CustomerServiceTest test = new CustomerServiceTest();
//        test.testAddCustomer();
//        test.testGetCustomerById();
//        test.testUpdateCustomer();
//        test.testDeleteCustomer();
//         test.testAuthenticateCustomer();
//         test.testCustomerPassword();
//        test.testGetAllCustomers();
    }
}
