package daoImpl;

import dao.TicketDao;
import exception.BookingException;
import model.Customer;
import model.Ticket;
import utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDaoImpl implements TicketDao {
    private Connection connection;
    Customer customer = null;
    PreparedStatement ppst = null;
    ResultSet rs = null;

    public TicketDaoImpl() {
        this.connection = DBConnection.getConnection();;
    }

    //1 ticket id
    @Override
    public Ticket getTicketById(int reservationId)throws BookingException {
        String sql ="SELECT * FROM reservations WHERE reservation_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,reservationId);
            rs = ppst.executeQuery();
            if (rs.next()){
                return new Ticket(
                        rs.getInt("reservation_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getString("status")
                );
            }
        else{
            return null;
        }
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Booking exception");
        }
    }

//2  get all tickets which are reserved
    @Override
    public List<Ticket> getAllTickets()throws BookingException {
        List <Ticket> tickets = new ArrayList<>();
        String sql ="SELECT * FROM reservations";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            rs =ppst.executeQuery();
            while (rs.next()){
                tickets.add(new Ticket(
                        rs.getInt("reservation_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getString("status")
                        )
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Booking exception");
        }
        return tickets;
    }

    //3 add ticket or booking reservation
    @Override
    public void addTicket(Ticket ticket)throws BookingException {
        String sql ="INSERT INTO reservations(reservation_id,bus_id,customer_id,reservation_date,status) VALUES(?,?,?,?,?)";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1, ticket.getreservationId());
            ppst.setInt(2,ticket.getBusId());
            ppst.setInt(3,ticket.getCustomerId());
            ppst.setDate(4,java.sql.Date.valueOf(ticket.getReservationDate()));
            ppst.setString(5,ticket.getStatus());
            ppst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Booking exception");
        }

    }
//  4   update ticket booking
    @Override
    public void updateTicket(Ticket ticket)throws BookingException {
        String sql ="UPDATE reservations SET bus_id=?, customer_id=?,reservation_date =?,status =? WHERE reservation_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,ticket.getBusId());
            ppst.setInt(2,ticket.getCustomerId());
            ppst.setDate(3,java.sql.Date.valueOf(ticket.getReservationDate()));
            ppst.setString(4,ticket.getStatus());
            ppst.setInt(5,ticket.getreservationId());
            ppst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Failed to update reservation");
        }
    }

//  5  Delete booked ticket
    @Override
    public void deleteTicket(int reservationId)throws BookingException {
        String sql ="DELETE FROM reservations WHERE reservation_id =?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,reservationId);
           int ans =  ppst.executeUpdate();
            if (ans==0){
                throw new BookingException("Ticket with id: " + reservationId + " not found.");
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Failed to delete reservation.");
        }

    }

// 6 get all tickets by customer id
    @Override
    public List<Ticket> getTicketByCustomerId(int customerId)throws BookingException {
        List<Ticket> tickets = new ArrayList<>();
        String sql ="SELECT * FROM reservations WHERE customer_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,customerId);
            rs = ppst.executeQuery();
            while (rs.next()){
                tickets.add(new Ticket(
                        rs.getInt("reservation_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getString("status")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Booking exception cannot get ticket by customer id.");
        }
        return tickets;
    }

//7  get all tickets with the help of bus_id
    @Override
    public List<Ticket> getTicketByBusId(int busId)throws BookingException {
        List<Ticket> tickets = new ArrayList<>();
        String sql ="SELECT * FROM reservations WHERE bus_id =?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,busId);
            rs= ppst.executeQuery();
            while (rs.next()){
                tickets.add(new Ticket(
                        rs.getInt("reservation_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("reservation_date").toLocalDate(),
                        rs.getString("status")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new BookingException("Booking exception cannot get ticket with bus id.");
        }
        return tickets;
    }
}
