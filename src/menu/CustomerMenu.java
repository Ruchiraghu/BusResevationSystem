package menu;

import BusManagement.BusManager;
import BusManagement.TicketManager;
import model.Customer;
import service.CustomerService;

import java.util.Scanner;

public class CustomerMenu {
    private Scanner sc = new Scanner(System.in);
    private final CustomerService customerService;
    private final TicketManager ticketManager;
    private final BusManager busManager;

    public CustomerMenu(Scanner sc, CustomerService customerService, TicketManager ticketManager, BusManager busManager) {
        this.sc = sc;
        this.customerService = customerService;
        this.ticketManager = ticketManager;
        this.busManager = busManager;
    }

    public void handleCustomerMenu() {
        boolean userLoggedIn = false;

        while (true) {
            if (!userLoggedIn) {
                System.out.println("Customer Menu:");
                System.out.println("1. Login");
                System.out.println("2. Create Account");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> userLoggedIn = login();
                    case 2 -> createAccount();
                    case 3 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Customer Menu:\n" +
                        "1. Book Ticket\n" +
                        "2. View Bookings\n" +
                        "3. Cancel Booking\n" +
                        "4. View Buses by Location or Route\n" +
                        "5. Logout\n" +
                        "Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> ticketManager.bookTicket();
                    case 2 -> ticketManager.viewBookings();
                    case 3 -> ticketManager.cancelBooking();
                    case 4-> busManager.displayAvailableBusesByLocationOrRoute();
                    case 5 -> {
                        userLoggedIn = false;
                        System.out.println("Logged out successfully.");
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private boolean login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try {
            boolean success = customerService.authenticateCustomer(email, password);
            if (success) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private void createAccount() {
        System.out.println("Enter customer id: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        try {
            Customer customer = new Customer(id, name, email, phoneNumber, password); // ID is 0 for new accounts
            customerService.addCustomer(customer);
            System.out.println("Account created successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
