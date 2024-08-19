package BusManagement;
import exception.BookingException;
import exception.BusNotFoundException;
import model.Bus;
import service.AdminService;
import service.BusService;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class BusManager {
    private final Scanner sc;
    private final BusService busService;
    private final AdminService adminService;

    public BusManager(Scanner sc, BusService busService, AdminService adminService) {
        this.sc = sc;
        this.busService = busService;
        this.adminService = adminService;
    }

    public void handleBusManagement() throws BusNotFoundException {
        boolean goBackToAdminMenu= true;
        while (goBackToAdminMenu){
        System.out.println("Bus Management:\n1. Add Bus\n2. Delete Bus\n3. Update Bus\n4. Get Bus By ID\n5. Get All Buses\n6.Go Back to Admin Menu\nEnter your choice: ");
        int busChoice = sc.nextInt();
        sc.nextLine();

        switch (busChoice) {
            case 1 -> addBus();
            case 2 -> deleteBus();
            case 3 -> updateBus();
            case 4 -> getBusById();
            case 5 -> getAllBuses();
            case 6->{ goBackToAdminMenu=false;
                System.out.println("Exiting bus management menu.");}
            default -> System.out.println("Invalid choice. Please try again!");
        }
        }
    }
    private void addBus() {
        System.out.println("Enter bus id: ");
        int busId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter bus name: ");
        String busName = sc.nextLine();
        System.out.println("Enter bus type: ");
        String busType = sc.nextLine();
        System.out.println("Enter available seats: ");
        int availableSeats = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter bus departure time: ");
        String departureTime = sc.nextLine();
        System.out.println("Enter bus arrival time: ");
        String arrivalTime = sc.nextLine();
        System.out.println("Enter route: ");
        String route = sc.nextLine();
        try {
            Bus bus = new Bus(busId, busName, busType, availableSeats, departureTime, arrivalTime, route);
            busService.addBus(bus);
            System.out.println("Bus added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

        }
    }

    private void deleteBus() {
        System.out.print("Enter bus ID to delete: ");
        int busId = sc.nextInt();
        sc.nextLine();  // Consume newline

        System.out.print("Enter admin username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter admin password for authentication: ");
        String password = sc.nextLine().trim();

        try {
            // Authenticate admin using username and password
            boolean isAuthenticated = adminService.authenticateAdmin(username, password);

            if (isAuthenticated) {
                // If authentication is successful, delete the bus
                busService.deleteBus(busId);
                System.out.println("Bus deleted successfully.");
            } else {
                // If authentication fails, notify the user
                System.out.println("Authentication failed. Bus deletion aborted.");
            }
        } catch (BusNotFoundException e) {
            // Handle case where the bus ID is not found
            System.out.println("Error: Bus with ID " + busId + " not found.");
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void getBusById() {
        System.out.println("Enter bus ID to get details:");
        int busId = sc.nextInt();
        try {
            Bus bus = busService.getById(busId);
            System.out.println("Bus Details: " + bus);
        } catch (BusNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void getAllBuses() throws BusNotFoundException {
        // Print table header
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-7s | %-14s | %-14s | %-15s |\n",
                "ID", "Name", "Type", "Departure Time", "Arrival Time", "Available Seats");
        System.out.println("---------------------------------------------------------------------------------------------");

        // Print bus details
        for (Bus b : busService.getAllBuses()) {
            System.out.printf("| %-4d | %-20s | %-7s | %-14s | %-14s | %-15d |\n",
                    b.getBusId(), b.getBusName(), b.getBusType(), b.getDepartureTime(), b.getArrivalTime(), b.getAvailableSeats());
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void updateBus() {
        System.out.println("Enter bus id to update: ");
        int busId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new bus name: ");
        String busName = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter new bus type: ");
        String busType = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter new available seats: ");
        int availableSeats = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter new bus departure time: ");
        String departureTime = sc.nextLine();
        System.out.println("Enter new bus arrival time: ");
        String arrivalTime = sc.nextLine();
        System.out.println("Enter new route: ");
        String route = sc.nextLine();
        try {
            Bus bus = new Bus(busId, busName, busType, availableSeats, departureTime, arrivalTime, route);
            busService.updateBus(bus);
            System.out.println("Bus updated successfully.");
        } catch (BusNotFoundException e) {
            System.out.println("Error updating bus: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void displayAvailableBusesByLocationOrRoute() {
        System.out.print("Enter location or route: ");
        String locationOrRoute = sc.nextLine().toLowerCase();
        try {
            List<Bus> buses = busService.getBusesByLocationOrRoute(locationOrRoute);
            if (buses.isEmpty()) {
                System.out.println("No buses found for the specified location/route.");
                return;
            }
            System.out.println("Buses found for location/route: " + locationOrRoute);
            String format = "%-5s %-20s %-15s %-15s %-20s %-20s %-30s%n";
            System.out.printf(format, "ID", "Name", "Type", "Available Seats", "Departure Time", "Arrival Time", "Route");
            System.out.println(new String(new char[130]).replace("\0", "-")); // Line separator

            for (Bus bus : buses) {
                int availableSeats = busService.calculateAvailableSeats(bus.getBusId());
                System.out.printf(format,
                        bus.getBusId(),
                        bus.getBusName(),
                        bus.getBusType(),
                        availableSeats,
                        bus.getDepartureTime(),
                        bus.getArrivalTime(),
                        bus.getRoute()
                );
            }
            System.out.println(new String(new char[130]).replace("\0", "-"));
        } catch (Exception e) {
            System.out.println("Error fetching buses: " + e.getMessage());
        }
    }

}
