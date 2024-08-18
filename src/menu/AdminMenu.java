package menu;

import BusManagement.AdminManager;
import BusManagement.BusManager;
import BusManagement.CustomerManager;
import BusManagement.TicketManager;
import exception.BusNotFoundException;
import service.AdminService;
import service.BusService;
import service.CustomerService;
import service.TicketService;

import java.util.Scanner;

public class AdminMenu {
    private final Scanner sc;
    private final AdminService adminService;
    private final BusService busService;
    private final CustomerService customerService;
    private final TicketService ticketService;

    public AdminMenu(Scanner sc, AdminService adminService, BusService busService, CustomerService customerService, TicketService ticketService) {
        this.sc = sc;
        this.adminService = adminService;
        this.busService = busService;
        this.customerService = customerService;
        this.ticketService = ticketService;
    }

    public void handleAdminFunctionalities() throws BusNotFoundException {
        AdminManager adminManager = new AdminManager(sc, adminService, busService, customerService);
        BusManager busManager = new BusManager(sc, busService, adminService);
        CustomerManager customerManager = new CustomerManager(sc, customerService);
        TicketManager ticketManager = new TicketManager(sc, ticketService, busService);

        boolean adminLoggedIn = false;
        while (true) {
            if (!adminLoggedIn) {
                System.out.println("Bus Ticket Reservation System Menu:\n1. Admin Login\n2. Create Admin Account\n3. Exit\nEnter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Clear the newline character from the buffer

                switch (choice) {
                    case 1 -> adminLoggedIn = adminManager.handleAdminLogin();
                    case 2 -> adminManager.handleAdminAccountCreation();
                    case 3 -> {
                        System.out.println("Exiting...");
                        return; // Exit the application
                    }
                    default -> System.out.println("Invalid choice. Please try again!");
                }
            } else {
                boolean stayLoggedIn = true;
                while (stayLoggedIn) {
                    System.out.println("Admin Menu:\n1. Manage Buses\n2. Manage Admins\n3. Manage Customers\n4. Logout\nEnter your choice:");
                    int adminChoice = sc.nextInt();
                    sc.nextLine(); // Clear the newline character from the buffer

                    switch (adminChoice) {
                        case 1 -> busManager.handleBusManagement(); // Calls the Bus Management method
                        case 2 -> adminManager.handleAdminManagement();
                        case 3 -> customerManager.handleCustomerManagement();
                        case 4 -> {
                            adminLoggedIn = false;
                            stayLoggedIn = false; // Exit Admin Menu loop
                            System.out.println("Logged out successfully.");
                        }
                        default -> System.out.println("Invalid choice. Please try again!");
                    }
                }
            }
        }
    }
}
