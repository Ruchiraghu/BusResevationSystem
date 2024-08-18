package daoImpl;

import dao.BusDao;
import exception.BusNotFoundException;
import model.Bus;
import utility.DBConnection;
import utility.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusDaoImpl implements BusDao {
    private Connection connection;
    Bus bus=null;
    PreparedStatement ppst = null;
    ResultSet rs = null;
    public BusDaoImpl(){
        this.connection = DBConnection.getConnection();
    }
// 1 getbusid
    @Override
    public Bus getBusById(int busId) throws BusNotFoundException {
        String msg = null;
        String sql = "SELECT * FROM buses WHERE bus_id=?";
        try {
            ppst = connection.prepareStatement(sql);
            ppst.setInt(1,busId);
            rs = ppst.executeQuery();
            if(rs.next()){
                return new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_name"),
                        rs.getString("bus_type"),
                        rs.getInt("available_seats"),
                        rs.getString("departure_time"),
                        rs.getString("arrival_time"),
                        rs.getString("route")
                );
            }else{
                Logger.log("Bus with Id " + busId + " not found.");
                throw new BusNotFoundException("Bus with Id "+busId+" not found.");
            }
        } catch (SQLException e) {
            throw new BusNotFoundException("Error retrieving the data:  "+e.getMessage());
        }
    }
//  2  getAllBuses

    @Override
    public List<Bus> getAllBuses () throws BusNotFoundException{
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";
       try(PreparedStatement ppst = connection.prepareStatement(sql);
        ResultSet rs = ppst.executeQuery()){
           while (rs.next()){
               Bus bus = new Bus(rs.getInt("bus_id"),
                       rs.getString("bus_name"),
                       rs.getString("bus_type"),
                       rs.getInt("available_seats"),
                       rs.getString("departure_time"),
                       rs.getString("arrival_time"),
                       rs.getString("route")
               );
               buses.add(bus);
           }
        }catch (SQLException e){
           Logger.log("Error retrieving all buses: "+e.getMessage());
           throw new BusNotFoundException("Bus not found");
       }
        return buses;
    }

//  3  addBuses
    @Override
    public void addBus(Bus bus) throws BusNotFoundException {
        String sql = "INSERT INTO buses (bus_id, bus_name, bus_type, available_seats, departure_time, arrival_time, route) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ppst = connection.prepareStatement(sql)) {
            ppst.setInt(1, bus.getBusId()); // Ensure busId is correctly set
            ppst.setString(2, bus.getBusName());
            ppst.setString(3, bus.getBusType());
            ppst.setInt(4, bus.getAvailableSeats());
            ppst.setString(5, bus.getDepartureTime());
            ppst.setString(6, bus.getArrivalTime());
            ppst.setString(7, bus.getRoute());
            ppst.executeUpdate();
        } catch (SQLException e) {
            Logger.log("Error adding bus: " + e.getMessage());
            throw new BusNotFoundException("Unable to add bus");
        }
    }

//   4 updateBus
    @Override
    public void updateBus(Bus bus) throws BusNotFoundException{
        String sql = "UPDATE buses SET bus_name=?,bus_type=?,available_seats=?,departure_time=?,arrival_time=?,route=? WHERE bus_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setString(1, bus.getBusName());
            ppst.setString(2, bus.getBusType());
            ppst.setInt(3,bus.getAvailableSeats());
            ppst.setString(4,bus.getDepartureTime());
            ppst.setString(5, bus.getArrivalTime());
            ppst.setString(6, bus.getRoute());
            ppst.setInt(7,bus.getBusId());
            System.out.println("Executing SQL: " + sql);
            System.out.println("Parameters: " + bus.getBusName() + ", " + bus.getBusType() + ", " + bus.getAvailableSeats() + ", " + bus.getDepartureTime() + ", " + bus.getArrivalTime() + ", " + bus.getRoute() + ", " + bus.getBusId());
            int rowUpdated = ppst.executeUpdate();
            if (rowUpdated==0){
                Logger.log("Bus with id: " + bus.getBusId() + " not found.");
                throw new BusNotFoundException("Bus with id: "+bus.getBusId()+" not found.");
            }
        }catch (SQLException e){
            Logger.log("Error updating bus: " + e.getMessage());
            throw new BusNotFoundException("Unable to update bus");
        }
    }

//    5 delete bus
    @Override
    public void deleteBus(int busId)throws BusNotFoundException {
        String sql = "DELETE FROM buses WHERE bus_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,busId);
            ppst.executeUpdate();
        }  catch (SQLException e){
            Logger.log("Error deleting bus with id " + busId + ": " + e.getMessage());
            throw new BusNotFoundException("Unable to delete bus.");
        }
    }

    @Override
    public void updateAvailableSeats(int busId, int seats) throws BusNotFoundException {
        String sql = "UPDATE buses SET available_seats=? WHERE bus_id=?";
        try (PreparedStatement ppst = connection.prepareStatement(sql)) {
            ppst.setInt(1, seats);
            ppst.setInt(2, busId);
            int rowsUpdated = ppst.executeUpdate();
            if (rowsUpdated == 0) {
                Logger.log("Bus with id: " + busId + " not found.");
                throw new BusNotFoundException("Bus with id: " + busId + " not found.");
            }
        } catch (SQLException e) {
            Logger.log("Error updating available seats: " + e.getMessage());
            throw new BusNotFoundException("Unable to update available seats.");
        }
    }

    @Override
    public int getTotalSeats(int busId) throws BusNotFoundException {
        String sql = "SELECT available_seats FROM buses WHERE bus_id=?";
        try (PreparedStatement ppst = connection.prepareStatement(sql)) {
            ppst.setInt(1, busId);
            rs = ppst.executeQuery();
            if (rs.next()) {
                return rs.getInt("available_seats");
            } else {
                Logger.log("Bus with id: " + busId + " not found.");
                throw new BusNotFoundException("Bus with id: " + busId + " not found.");
            }
        } catch (SQLException e) {
            Logger.log("Error retrieving total seats: " + e.getMessage());
            throw new BusNotFoundException("Unable to retrieve total seats.");
        }
    }

    public List<Bus> findBusesByLocationOrRoute(String locationOrRoute) {
        String sql = "SELECT * FROM buses WHERE route LIKE ?";
        try (PreparedStatement ppst = connection.prepareStatement(sql)) {
            ppst.setString(1, "%" + locationOrRoute + "%");
            ResultSet rs = ppst.executeQuery();
            List<Bus> buses = new ArrayList<>();
            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_name"),
                        rs.getString("bus_type"),
                        rs.getInt("available_seats"),
                        rs.getString("departure_time"),
                        rs.getString("arrival_time"),
                        rs.getString("route")
                );
                buses.add(bus);
            }
            return buses;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public int getTotalSeatsForBus(int busId) {
        String sql = "SELECT available_seats FROM buses WHERE bus_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, busId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("available_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get the number of seats that have been booked for a specific bus
    public int getBookedSeatsForBus(int busId) {
        String sql = "SELECT COUNT(*) AS booked_seats FROM reservations WHERE bus_id = ? AND status = 'booked'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, busId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("booked_seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}