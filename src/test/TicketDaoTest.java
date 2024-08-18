package test;

import daoImpl.TicketDaoImpl;
import exception.BookingException;
import model.Ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketDaoTest {
    private TicketDaoImpl ticketDao;

    public void setUp() {
        ticketDao = new TicketDaoImpl();
    }

    //   Test--1 ticket by id
    public void testTicketById() {
        setUp();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate travelDate = LocalDate.parse("2024-08-12",formatter);
            Ticket newTicket = new Ticket(7, 1, 8, travelDate, "Booked");
            ticketDao.addTicket(newTicket);
            Ticket addedTicket = ticketDao.getTicketById(7);
            if (addedTicket!=null&&addedTicket.getBusId()==1){
                System.out.println("testTicketById: passed.");
            }  else {
                System.out.println("testTicketById: failed.");
            }
        } catch (Exception e) {
            System.out.println("Exception came while getting ticket id.");
            e.printStackTrace();
        }
    }

//    Test--2 add ticket by id
    public void testAddTicket(){
        setUp();
        try{ DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate travelDate = LocalDate.parse("2024-09-23",formatter);
            Ticket ticket = new Ticket(28,11,3,travelDate,"Booked");
//            Add ticket
            ticketDao.addTicket(ticket);

//            fetch ticket by id to verify it added correctly
            Ticket addedTicket = ticketDao.getTicketById(28);
            if (addedTicket!=null&& addedTicket.getBusId()==11&&
                    addedTicket.getCustomerId() == 3 &&
                    addedTicket.getReservationDate().equals(travelDate) &&
                    addedTicket.getStatus().equals("Booked")){
                System.out.println("testAddTicket: passed");
            }else {
                System.out.println("testAddTicket: failed");
            }
        }catch (Exception e){
            System.out.println("Exception occurred while adding ticket");
        }
    }

//    test--3 update ticket
    public void testUpdateTicket(){
        setUp();
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newTravelDate = LocalDate.parse("2024-09-30", formatter);

            //update ticket
            Ticket updateTicket = new Ticket(14,12,8,newTravelDate,"Cancelled");
            ticketDao.updateTicket(updateTicket);
            Ticket fetchedTicket = ticketDao.getTicketById(14);
            if (fetchedTicket!=null&&
            fetchedTicket.getReservationDate().equals(newTravelDate)&&
            fetchedTicket.getStatus().equalsIgnoreCase("Cancelled")){
                System.out.println("testUpdateTicket: passed.");
            }else{
                System.out.println("testUpdateTicket: failed!");
            }
        }
        catch (Exception e){
            System.out.println("Exception occurred while updating ticket.");
            e.printStackTrace();
        }    }

//    Test--4 delete ticket
public void testDeleteTicket() {
    setUp();
    try {
        // Attempt to delete the ticket
        ticketDao.deleteTicket(11);
        Ticket  ticket=ticketDao.getTicketById(11);
        if (ticket==null){
            System.out.println("testDeleteTicket: passed.");
        }else {
            System.out.println("testDeleteTicket: failed!");
        }
    } catch (Exception e) {
        System.out.println("Exception occurred while deleting ticket.");
        e.printStackTrace();
    }
}

//Test--5 get all tickets
public void testGetAllTickets() {
    setUp();
    try {
        List<Ticket> tickets = ticketDao.getAllTickets();

        boolean foundTicket1 = tickets.stream().anyMatch(t -> t.getreservationId() == 8);
        boolean foundTicket2 = tickets.stream().anyMatch(t -> t.getreservationId() == 9);

        if (foundTicket1 && foundTicket2) {
            System.out.println("testGetAllTickets: passed.");
        } else {
            System.out.println("testGetAllTickets: failed.");
        }
    } catch (Exception e) {
        System.out.println("Exception occurred while getting all tickets.");
        e.printStackTrace();
    }
}

//Test--6 tickets by customer id
public void testGetTicketByCustomerId() {
    setUp();
    try {
        List<Ticket> tickets = ticketDao.getTicketByCustomerId(9);

        boolean found = tickets.stream().anyMatch(t -> t.getreservationId() == 34);

        if (found) {
            System.out.println("testGetTicketByCustomerId: passed.");
        } else {
            System.out.println("testGetTicketByCustomerId: failed.");
        }
    } catch (Exception e) {
        System.out.println("Exception occurred while getting tickets by customer ID.");
        e.printStackTrace();
    }
}

//Test--7 tickets by customer id
public void testGetTicketByBusId() {
    setUp();
    try {
        List<Ticket> tickets = ticketDao.getTicketByBusId(11);

        boolean found = tickets.stream().anyMatch(t -> t.getreservationId() == 34);

        if (found) {
            System.out.println("testGetTicketByBusId: passed.");
        } else {
            System.out.println("testGetTicketByBusId: failed.");
        }
    } catch (Exception e) {
        System.out.println("Exception occurred while getting tickets by bus ID.");
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        TicketDaoTest test = new TicketDaoTest();
//        test.testTicketById();
//        test.testAddTicket();
//        test.testUpdateTicket();
//        test.testDeleteTicket();
//        test.testGetAllTickets();
//        test.testGetTicketByCustomerId();
//        test.testGetTicketByBusId();
    }
}