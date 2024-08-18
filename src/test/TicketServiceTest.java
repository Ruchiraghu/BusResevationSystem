package test;

import daoImpl.TicketDaoImpl;
import exception.BookingException;
import model.Ticket;
import service.TicketService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketServiceTest {
    private TicketService ticketService;
    private TicketDaoImpl ticketDao;

    public void setUp(){
        ticketDao = new TicketDaoImpl();
        ticketService = new TicketService(ticketDao);
    }

//    Test--1 get ticket by id
    public void testGetTicketById(){
        setUp();
        try{ Ticket ticket = ticketService.getTicketById(7);
            if (ticket!=null&&ticket.getReservationId()==7){
                System.out.println("testGetTicketById: passed.");
            }   else {
                System.out.println("testGetTicketById: failed.");
            }
        }catch (Exception e){
            System.out.println("Exception occurred while getting ticket by ID.");
            e.printStackTrace();
        }
    }

//    Test--2 add ticket
    public void testAddTicket(){
        setUp();
        try{ DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate travelDate = LocalDate.parse("2024-11-28", formatter);
            Ticket ticket = new Ticket(48,11,travelDate,"Booked");
//            System.out.println("Adding ticket: " + ticket);

            ticketService.addTicket(ticket);
//            System.out.println("Ticket added. Now trying to retrieve...");

            Ticket addedTicket = ticketService.getTicketById(48);
            if (addedTicket != null && addedTicket.getBusId() == 11 &&
                    addedTicket.getCustomerId() == 9 &&
                    addedTicket.getReservationDate().equals(travelDate) &&
                    addedTicket.getStatus().equals("Booked")) {
                System.out.println("testAddTicket: passed.");
            } else {
                System.out.println("testAddTicket: failed.");
                System.out.println("Added ticket details: " + addedTicket);
            }
        }catch (Exception e){
            System.out.println("Exception occurred while adding ticket.");
            e.printStackTrace();
        }
    }

//    Test-3 update ticket
    public void testUpdateTicket(){
        setUp();
        try{ DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate travelDate = LocalDate.parse("2024-08-12", formatter);
            Ticket ticket = new Ticket(7,1,travelDate,"Cancelled");
            ticketService.updateTicket(ticket);
            if (ticket!=null&&ticket.getReservationDate().equals(travelDate)&&
            ticket.getStatus().equalsIgnoreCase("cancelled")){
                System.out.println("testUpdateTicket: passed.");
            }     else {
                System.out.println("testUpdateTicket: failed!");
            }   }catch (Exception e){
            System.out.println("Exception occurred while updating ticket.");
            e.printStackTrace();
        }
    }

//    Test--4 Delete ticket
    public void testDeleteTicket(){
        setUp();
        try{
            ticketService.deleteTicket(8);
            Ticket ticket = ticketService.getTicketById(8);
            if (ticket==null){
                System.out.println("testDeleteTicket: passed.");
            }else {
                System.out.println("testDeleteTicket: failed!");
            }
        }catch (Exception e){
            System.out.println("Exception occurred while deleting ticket");
            e.printStackTrace();
        }
    }
    public void testGetTicketsByCustomerId() {
        setUp();
        try {
            List<Ticket> tickets = ticketService.getTicketByCustomerId(9);
            if (tickets != null && !tickets.isEmpty()) {
                System.out.println("testGetTicketsByCustomerId: passed.");
            } else {
                System.out.println("testGetTicketsByCustomerId: failed.");
            }
        } catch (BookingException e) {
            System.out.println("Exception occurred while getting tickets by customer ID.");
            e.printStackTrace();
        }
    }

    // Test--6 get tickets by bus ID
    public void testGetTicketsByBusId() {
        setUp();
        try {
            List<Ticket> tickets = ticketService.getTicketByBusId(1);
            if (tickets != null && !tickets.isEmpty()) {
                System.out.println("testGetTicketsByBusId: passed.");
            } else {
                System.out.println("testGetTicketsByBusId: failed.");
            }
        } catch (BookingException e) {
            System.out.println("Exception occurred while getting tickets by bus ID.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TicketServiceTest test = new TicketServiceTest();
//        test.testGetTicketById();
//        test.testAddTicket();
//        test.testUpdateTicket();
//        test.testDeleteTicket();
//        test.testGetTicketsByCustomerId();
//        test.testGetTicketsByBusId();
    }
}
