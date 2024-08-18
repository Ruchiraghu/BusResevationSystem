package dao;

import exception.BusNotFoundException;
import model.Bus;

import java.util.List;

public interface BusDao {
    Bus getBusById(int busId) throws BusNotFoundException;
    List<Bus> getAllBuses() throws BusNotFoundException;
    void addBus(Bus bus) throws BusNotFoundException;
    void updateBus(Bus bus) throws BusNotFoundException;
    void deleteBus(int busId) throws BusNotFoundException;

    void updateAvailableSeats(int busId, int i) throws BusNotFoundException;

    int getTotalSeats(int busId) throws BusNotFoundException;

    List<Bus> findBusesByLocationOrRoute(String locationOrRoute);

    int getBookedSeatsForBus(int busId);

    int getTotalSeatsForBus(int busId);
}
