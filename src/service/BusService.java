package service;

import dao.BusDao;
import dao.TicketDao;
import daoImpl.BusDaoImpl;
import exception.BookingException;
import exception.BusNotFoundException;
import model.Bus;
import test.BusServiceTest;

import java.util.List;
import java.util.stream.Collectors;

public class BusService {
    private BusDao busDao;
    private TicketDao ticketDao;

    public BusService(BusDao busDao)
    {
        this.busDao = busDao;
    }
    public BusService() {
        this.busDao = new BusDaoImpl();
    }
    //1 get bus by id
    public Bus getById(int busId) throws BusNotFoundException {
        return busDao.getBusById(busId);
    }

//  2  list of all buses
    public List<Bus> getAllBuses(){
        try {
            return busDao.getAllBuses();
        } catch (BusNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

//  3  add bus
    public void addBus(Bus bus) throws BusNotFoundException {
        validateBus(bus);
        busDao.addBus(bus);
    }

//  4  update bus
    public void updateBus(Bus bus)throws BusNotFoundException{
        Bus existingBus = busDao.getBusById(bus.getBusId());
        if (existingBus==null){
            throw new BusNotFoundException("Bus with id "+bus.getBusId()+" does not found.");
        }
        busDao.updateBus(bus);
    }

//  5  delete bus
    public void deleteBus(int busId) throws BusNotFoundException{
        Bus existingBus = busDao.getBusById(busId);
        if (existingBus==null){
            throw new BusNotFoundException("Bus with id "+busId+" does not exists.");
        }
        busDao.deleteBus(busId);
    }
//  6  Create Bus validation
    private void validateBus(Bus bus){
        if (bus.getBusName()==null||bus.getBusName().isEmpty()){
            throw new IllegalArgumentException("Bus name cannot be empty.");
        }
        if(bus.getAvailableSeats()<0){
            throw new IllegalArgumentException("Available seats cannot be negative.");
        }
    }
// 7   Available seats
public List<Bus> getAvailableBuses() {
    try {
        // Get all buses from the data source
        List<Bus> buses = busDao.getAllBuses();
        // Filter buses with available seats
        return buses.stream()
                .filter(bus -> bus.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    } catch (BusNotFoundException e) {
        throw new RuntimeException("Error fetching available buses: " + e.getMessage());
    }
}

    public void bookSeats(int busId, int numberOfSeats) throws BookingException, BusNotFoundException {
        Bus bus = getById(busId);
        int availableSeats = bus.getAvailableSeats();
        System.out.println("Current available seats: " + availableSeats);

        if (availableSeats >= numberOfSeats) {
            bus.setAvailableSeats(availableSeats - numberOfSeats);
            System.out.println("New available seats: " + bus.getAvailableSeats());
            updateBus(bus);
        } else {
            throw new BookingException("Not enough seats available.");
        }
    }


    public void cancelBooking(int busId, int numberOfSeats) throws BookingException, BusNotFoundException {
        Bus bus = getById(busId); // Fetch the bus using the ID
        int availableSeats = bus.getAvailableSeats();

        // Increase the number of available seats
        bus.setAvailableSeats(availableSeats + numberOfSeats);
        updateBus(bus); // Save the updated bus details
    }




    public int calculateAvailableSeats(int busId) {
        try {
            // Get the total number of seats for the bus
            int totalSeats = busDao.getTotalSeatsForBus(busId);

            // Get the number of seats that have been booked
            int bookedSeats = busDao.getBookedSeatsForBus(busId);

            // Calculate the number of available seats
            int availableSeats = totalSeats - bookedSeats;

            // Return the number of available seats
            return availableSeats;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // Return 0 in case of an error
        }
    }
    public List<Bus> getBusesByLocationOrRoute(String locationOrRoute) {
        String lowerCaseQuery = locationOrRoute.toLowerCase();
        try {
            // Get all buses from the data source
            List<Bus> buses = busDao.getAllBuses();
            // Filter buses based on route or location
            return buses.stream()
                    .filter(bus -> bus.getRoute().toLowerCase().contains(lowerCaseQuery))
                    .collect(Collectors.toList());
        } catch (BusNotFoundException e) {
            throw new RuntimeException("Error fetching buses by location or route: " + e.getMessage());
        }
    }


}
