package dao;

import exception.BookingException;
import model.Ticket;
import java.util.List;
public interface TicketDao {
    Ticket getTicketById(int ticketId) throws BookingException;
    List<Ticket> getAllTickets() throws BookingException;
    void addTicket(Ticket ticket) throws BookingException;
    void updateTicket(Ticket ticket) throws BookingException;
    void deleteTicket(int ticketId) throws BookingException;
    List<Ticket> getTicketByCustomerId(int customerId) throws BookingException;
    List<Ticket> getTicketByBusId(int busId) throws BookingException;

}
