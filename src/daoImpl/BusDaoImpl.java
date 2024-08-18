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
                bus = new Bus();
                bus.setBusId(rs.getInt("bus_id"));
                bus.setBusName(rs.getString("bus_name"));
                bus.setBusType(rs.getString("bus_type"));
                bus.setAvailableSeats(rs.getInt("available_seats"));
                bus.setDepartureTime(rs.getString("departure_time"));
                bus.setArrivalTime(rs.getString("arrival_time"));
                bus.setRoute(rs.getString("route"));
            }else{
                Logger.log("Bus with Id " + busId + " not found.");
                throw new BusNotFoundException("Bus with Id "+busId+" not found.");
            }
        } catch (SQLException e) {
            throw new BusNotFoundException("Error retrieving the data:  "+e.getMessage());
        }
        return bus;
    }
//  2  getAllBuses

    @Override
    public List<Bus> getAllBuses () throws BusNotFoundException{
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";
       try(PreparedStatement ppst = connection.prepareStatement(sql);
        ResultSet rs = ppst.executeQuery()){
           while (rs.next()){
               Bus bus = new Bus();
               bus.setBusId(rs.getInt("bus_id"));
               bus.setBusName(rs.getString("bus_name"));
               bus.setBusType(rs.getString("bus_type"));
               bus.setAvailableSeats(rs.getInt("available_seats"));
               bus.setDepartureTime(rs.getString("departure_time"));
               bus.setArrivalTime(rs.getString("arrival_time"));
               bus.setRoute(rs.getString("route"));
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
}
