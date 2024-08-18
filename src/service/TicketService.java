package service;

import dao.TicketDao;
import daoImpl.TicketDaoImpl;
import exception.BookingException;
import model.Ticket;

import java.util.List;

public class TicketService {
private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public TicketService() {
        this.ticketDao = new TicketDaoImpl();
    }

    //    Add ticket
    public void addTicket(Ticket ticket) throws BookingException{
        ticketDao.addTicket(ticket);
    }

//    get a ticket by its id
    public Ticket getTicketById(int reservationId) throws BookingException{
        return ticketDao.getTicketById(reservationId);
    }

//    get all tickets
    public List<Ticket> getAllTickets() throws BookingException {
        return ticketDao.getAllTickets();
    }

//        update existing ticket
    public void updateTicket(Ticket ticket) throws BookingException{
        ticketDao.updateTicket(ticket);
    }

//    delete ticket by id
    public boolean deleteTicket(int reservationId) throws BookingException{
        ticketDao.deleteTicket(reservationId);
        return false;
    }

//    get all ticket by specific customer_id
    public List<Ticket> getTicketByCustomerId(int customerId) throws BookingException{
        return ticketDao.getTicketByCustomerId(customerId);
    }

//    get all tickets by specific bus id
    public List<Ticket> getTicketByBusId(int busId) throws BookingException{
        return ticketDao.getTicketByBusId(busId);
    }
}
