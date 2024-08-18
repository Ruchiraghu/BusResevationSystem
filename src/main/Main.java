package main;

import BusManagement.TicketManager;
import exception.BusNotFoundException;
import menu.AdminMenu;
import menu.CustomerMenu;
import service.AdminService;
import service.BusService;
import service.CustomerService;
import service.TicketService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws BusNotFoundException {
        Scanner sc = new Scanner(System.in);

        // Initialize services
        AdminService adminService = new AdminService();
        BusService busService = new BusService();
        CustomerService customerService = new CustomerService();
        TicketService ticketService = new TicketService();

        // Create menu instances
        TicketManager ticketManager = new TicketManager(sc, ticketService, busService);
        AdminMenu adminMenu = new AdminMenu(sc, adminService, busService, customerService, ticketService);
        CustomerMenu customerMenu = new CustomerMenu(sc, customerService, ticketManager);

        while (true) {
            System.out.println("Bus Ticket Reservation System:");
            System.out.println("1. Admin Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> adminMenu.handleAdminFunctionalities();
                case 2 -> customerMenu.handleCustomerMenu();
                case 3 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return; // Exit the application
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
