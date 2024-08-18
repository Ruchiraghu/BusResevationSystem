package BusManagement;

import exception.CustomerNotFound;
import model.Customer;
import service.CustomerService;
import utility.EmailUtils;
import java.util.Scanner;

public class CustomerManager {
    private final Scanner sc;
    private final CustomerService customerService;

    public CustomerManager(Scanner sc, CustomerService customerService) {
        this.sc = sc;
        this.customerService = customerService;
    }

    public void handleCustomerManagement() {
        System.out.println("Customer Management:");
        System.out.println("1. Add Customer");
        System.out.println("2. Update Customer");
        System.out.println("3. Delete Customer");
        System.out.println("4. Get Customer By ID");
        System.out.println("5. Get All Customers");
        System.out.print("Enter your choice: ");
        int customerChoice = sc.nextInt();
        sc.nextLine();

        switch (customerChoice) {
            case 1 -> addCustomer();
            case 2 -> updateCustomer();
            case 3 -> deleteCustomer();
            case 4 -> getCustomerById();
            case 5 -> getAllCustomers();
            default -> System.out.println("Invalid choice. Please try again!");
        }
    }

    private void addCustomer() {
        System.out.println("Enter id: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter customer email: ");
        String email = sc.nextLine();
        if (!EmailUtils.isValidEmail(email)){
            System.out.println("Invalid email format.");
            return;
        }
        System.out.print("Enter customer phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();

        // Input validation (basic example)
        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required.");
            return;
        }

        try {
            Customer customer = new Customer(id, name, email, phoneNumber, password);
            customerService.addCustomer(customer);
            System.out.println("Customer added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateCustomer() {
        System.out.print("Enter customer ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter new customer name: ");
        String name = sc.nextLine();
        System.out.print("Enter new customer email: ");
        String email = sc.nextLine();
        if (!EmailUtils.isValidEmail(email)){
            System.out.println("Invalid email.");
            return;
        }
        System.out.print("Enter new customer phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.println("Enter new customer password: ");
        String password = sc.nextLine();

        // Input validation (basic example)
        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required.");
            return;
        }

        try {
            Customer customer = new Customer(id, name, email, phoneNumber, password);
            customerService.updateCustomer(customer);
            System.out.println("Customer updated successfully.");
        } catch (CustomerNotFound e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void deleteCustomer() {
        System.out.print("Enter customer ID to delete: ");
        int customerId = sc.nextInt();
        sc.nextLine(); // Consume newline

        try {
            customerService.deleteCustomer(customerId);
            System.out.println("Customer deleted successfully.");
        } catch (CustomerNotFound e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void getCustomerById() {
        System.out.print("Enter customer ID to get details: ");
        int customerId = sc.nextInt();
        sc.nextLine();  // Consume newline

        try {
            Customer customer = customerService.getCustomerById(customerId);

            // Printing the customer details in a table format
            System.out.println("------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-4d | %-20s | %-25s | %-15s | %-10s |\n",
                    customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber(), customer.getPassword());
            System.out.println("------------------------------------------------------------------------------------------------------------------");
        } catch (CustomerNotFound e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getAllCustomers() {
        try {
            var customers = customerService.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                for (Customer customer : customers) {
                    System.out.println(customer);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }
}
