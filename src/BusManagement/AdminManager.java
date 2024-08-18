package BusManagement;

import exception.AdminNotFoundException;
import exception.CustomerNotFound;
import exception.InvalidLoginException;
import model.Admin;
import model.Customer;
import service.AdminService;
import service.BusService;
import service.CustomerService;

import java.util.List;
import java.util.Scanner;

public class AdminManager {
    private final Scanner sc;
    private final AdminService adminService;
    private final BusService busService;
    private final CustomerService customerService;
    private boolean isAuthenticated = false;

    public AdminManager(Scanner sc, AdminService adminService, BusService busService, CustomerService customerService) {
        this.sc = sc;
        this.adminService = adminService;
        this.busService = busService;
        this.customerService = customerService;
    }

    // Handle admin login
    public boolean handleAdminLogin() {
        System.out.print("Enter admin username: ");
        String username = sc.nextLine();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        try {
            isAuthenticated = adminService.authenticateAdmin(username, password);
            if (isAuthenticated) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Authentication failed: Invalid username or password.\n");
                return false;
            }
        } catch (InvalidLoginException e) {
            System.out.println("Invalid login details: " + e.getMessage() + "\n");
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage() + "\n");
            return false;
        }
    }

    // Handle account creation for admins
    public void handleAdminAccountCreation() {
        if (adminService.getAdminCount() >= 5) {
            System.out.println("Cannot create more than 5 admin accounts.\n");
            return;
        }

        System.out.print("Admin ID: ");
        int adminId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter admin username/email: ");
        String username = sc.nextLine();
        System.out.print("Enter admin password: ");
        String password = sc.nextLine();

        try {
            Admin admin = new Admin(adminId, username, password);
            adminService.addAdmin(admin);
            System.out.println("Admin account created successfully.\n");

            // Automatically log in the newly created admin
            if (adminService.authenticateAdmin(username, password)) {
                System.out.println("Login successful!\n");
                BusManager busManager = new BusManager(sc, busService, adminService);
                busManager.handleBusManagement();
            }
        } catch (Exception e) {
            System.out.println("Error creating admin account: " + e.getMessage() + "\n");
        }
    }

    // Handle admin management tasks
    public void handleAdminManagement() {
        if (!isAuthenticated) {
            System.out.println("You need to be logged in as an admin to manage admins.\n");
            return;
        }

        boolean keepRunning = true;
        while (keepRunning) {
            System.out.print("Admin Management:\n" +
                    "1. Update Admin\n" +
                    "2. Delete Admin\n" +
                    "3. Get Admin By ID\n" +
                    "4. Get All Admins\n" +
                    "5. Go Back To The Admin Menu\n" +
                    "Enter your choice: ");
            int adminChoice = sc.nextInt();
            sc.nextLine();
            switch (adminChoice) {
                case 1 -> updateAdmin();
                case 2 -> deleteAdmin();
                case 3 -> getAdminById();
                case 4 -> getAllAdmins();
                case 5 -> keepRunning = false;
                default -> System.out.println("Invalid choice. Please try again!\n");
            }
        }
    }

    private void getAdminById() {
        System.out.print("Enter admin ID to get details: ");
        int adminId = sc.nextInt();
        sc.nextLine();

        try {
            Admin admin = adminService.getAdminById(adminId);
            System.out.println("Admin Details: " + admin + "\n");
        } catch (AdminNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    private void getAllAdmins() {
        try {
            for (Admin admin : adminService.getAllAdmins()) {
                System.out.println(admin + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error retrieving admins: " + e.getMessage() + "\n");
        }
    }

    private void updateAdmin() {
        System.out.print("Enter admin ID to update: ");
        int adminId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new admin username: ");
        String username = sc.nextLine();
        System.out.print("Enter new admin password: ");
        String password = sc.nextLine();
        try {
            Admin admin = new Admin(adminId, username, password);
            adminService.updateAdmin(admin);
            System.out.println("Admin updated successfully.\n");
        } catch (AdminNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage() + "\n");
        }
    }

    private void deleteAdmin() {
        System.out.print("Enter admin ID to delete: ");
        int adminId = sc.nextInt();
        sc.nextLine();

        try {
            adminService.deleteAdmin(adminId);
            System.out.println("Admin deleted successfully.\n");
        } catch (AdminNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
        }
    }

    // Handle customer management
    public void handleCustomerManagement() {
        boolean keepRunning = true;
        while (keepRunning) {
        System.out.println("Customer Management:\n" +
                "1. Get All Customers\n" +
                "2. Get Customer By ID\n" +
                "3. Go Back to Admin Menu\n" +
                "4. Exit Customer Management\n"+
                "Enter your choice:");
        int customerChoice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (customerChoice) {
            case 1 -> getAllCustomers();
            case 2 -> getCustomerById();
            case 3 -> {
                keepRunning = false;
                System.out.println("Exiting customer management menu.");
            }
            case 4 ->{
                keepRunning = false;
                System.out.println("Exiting customer management.");
            }
            default -> System.out.println("Invalid choice. Please try again!");
               }
        }
    }

    private void getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                printCustomerList(customers);
            }
        } catch (Exception e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }


    private void getCustomerById() {
        System.out.print("Enter customer ID to get details: ");
        int customerId = sc.nextInt();
        sc.nextLine(); // Consume newline

        try {
            Customer customer = customerService.getCustomerById(customerId);
            printCustomerDetails(customer);
        } catch (CustomerNotFound e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void printCustomerList(List<Customer> customers) {
        // Adjust column widths for better readability
        String border = "------------------------------------------------------------------------------------------------------------------";
        System.out.println(border);
        System.out.println("ID      | Name                    | Email                    | Phone Number         | Password           ");
        System.out.println(border);
        for (Customer customer : customers) {
            printCustomerDetails(customer);
        }
        System.out.println(border);
    }

    private void printCustomerDetails(Customer customer) {
        // Adjust formatting to match the column widths
        String id = String.format("%-6d", customer.getCustomerId());
        String name = String.format("%-22s", customer.getName());
        String email = String.format("%-25s", customer.getEmail());
        String phoneNumber = String.format("%-20s", customer.getPhoneNumber());
        String password = String.format("%-20s", customer.getPassword());

        System.out.println(id + " | " + name + " | " + email + " | " + phoneNumber + " | " + password);
    }

}
