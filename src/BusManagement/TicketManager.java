package BusManagement;
import exception.BookingException;
import exception.BusNotFoundException;
import model.Bus;
import model.Ticket;
import service.BusService;
import service.TicketService;
import utility.DateUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class TicketManager {
    private final Scanner sc;
    private final TicketService ticketService;
    private final BusService busService;

    public TicketManager(Scanner sc, TicketService ticketService, BusService busService) {
        this.sc = sc;
        this.ticketService = ticketService;
        this.busService = busService;

    }

    private void displayAvailableBuses() {
        try {
            List<Bus> buses = busService.getAllBuses();
            if (buses.isEmpty()) {
                System.out.println("No buses available.");
                return;
            }
            System.out.println("Available Buses:");
            String format = "%-5s %-20s %-15s %-15s %-20s %-20s %-30s%n";

            System.out.printf(format, "ID", "Name", "Type", "Seats", "Departure Time", "Arrival Time", "Route");
            System.out.println(new String(new char[105]).replace("\0", "-")); // Line separator

            // Print each bus
            for (Bus bus : buses) {
                System.out.printf(format,
                        bus.getBusId(),
                        bus.getBusName(),
                        bus.getBusType(),
                        bus.getAvailableSeats(),
                        bus.getDepartureTime(),
                        bus.getArrivalTime(),
                        bus.getRoute()
                );
            }
            System.out.println(new String(new char[105]).replace("\0", "-"));
        } catch (Exception e) {
            System.out.println("Error fetching available buses: " + e.getMessage());
        }
    }

    public void bookTicket() {
        System.out.println("-----Book Ticket-----");
        System.out.println("These are available buses: ");
        displayAvailableBuses();
        System.out.println("Enter bus ID: ");
        int busId = sc.nextInt();
        System.out.println("Enter customer ID: ");
        int customerId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter reservation date (YYYY-MM-DD): ");
        String reserveDate = sc.nextLine();
        LocalDate reservationDate = DateUtils.parseDate(reserveDate);
        if (reservationDate == null) {
            System.out.println("Invalid reservation date.");
            return;
        }
        System.out.println("Enter status: ");
        String status = sc.nextLine();
        try {
            // Create and add ticket
            Ticket ticket = new Ticket(busId, customerId, reservationDate, status);
            ticketService.addTicket(ticket);

            // Update available seats for the bus
            Bus bus = busService.getBusById(busId);
            if (bus != null) {
                int availableSeats = bus.getAvailableSeats();
                if (availableSeats > 0) {
                    bus.setAvailableSeats(availableSeats - 1); // Assuming booking 1 seat
                    busService.updateBus(bus);
                    System.out.println("Ticket booked successfully with Reservation ID:\n" + ticket.getReservationId() + "\nDate:\n" + ticket.getReservationDate());
                } else {
                    System.out.println("No available seats for the selected bus.");
                }
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("Error booking ticket: " + e.getMessage());
        }
    }


    private void cancelTicket() {
        System.out.print("Enter ticket ID to cancel: ");
        int ticketId = sc.nextInt();
        sc.nextLine();

        try {
            boolean success = ticketService.deleteTicket(ticketId);
            if (success) {
                System.out.println("Ticket canceled successfully.");
            } else {
                System.out.println("Error: Booking ID not found.");
            }
        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getTicketById() {
        System.out.print("Enter ticket ID to get details: ");
        int ticketId = sc.nextInt();
        sc.nextLine();

        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            System.out.println("Ticket Details: " + ticket);
        } catch (BookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getTicketsByCustomerId() {
        System.out.print("Enter customer ID to get tickets: ");
        int customerId = sc.nextInt();
        sc.nextLine();

        try {
            for (Ticket ticket : ticketService.getTicketByCustomerId(customerId)) {
                System.out.println(ticket);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getTicketsByBusId() {
        System.out.print("Enter bus ID to get tickets: ");
        int busId = sc.nextInt();
        sc.nextLine();

        try {
            for (Ticket ticket : ticketService.getTicketByBusId(busId)) {
                System.out.println(ticket);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewBookings() {
        System.out.print("Enter your customer ID to view bookings: ");
        int customerId = sc.nextInt();
        sc.nextLine(); // Consume newline

        try {
            var bookings = ticketService.getTicketByCustomerId(customerId);
            if (bookings.isEmpty()) {
                System.out.println("No bookings found.");
            } else {
                System.out.println("Your bookings:");
                for (Ticket ticket : bookings) {
                    System.out.println(ticket);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cancelBooking() {
        System.out.println("Cancel Booking:");
        System.out.print("Enter booking ID to cancel: ");
        int bookingId = sc.nextInt();
        sc.nextLine(); // Consume newline

        try {
            // Retrieve the ticket to cancel
            Ticket ticket = ticketService.getTicketById(bookingId);
            if (ticket != null) {
                // Delete the ticket
                ticketService.deleteTicket(bookingId);
                System.out.println("Booking canceled successfully.");

                // Retrieve the bus associated with the ticket
                Bus bus = busService.getBusById(ticket.getBusId());
                if (bus != null) {
                    // Update available seats
                    int currentSeats = bus.getAvailableSeats();
                    bus.setAvailableSeats(currentSeats + 1); // Increment seats for cancellation

                    // Update the bus with new seat count
                    busService.updateBus(bus);
                    System.out.println("Bus seats updated successfully.");
                } else {
                    System.out.println("Bus not found.");
                }
            } else {
                System.out.println("Error: Ticket not found.");
            }
        } catch (BookingException | BusNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }


}
