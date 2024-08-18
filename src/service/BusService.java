package service;

import dao.BusDao;
import daoImpl.BusDaoImpl;
import exception.BookingException;
import exception.BusNotFoundException;
import model.Bus;
import test.BusServiceTest;

import java.util.List;

public class BusService {
    private BusDao busDao;

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
public List<Bus> getAvailableBuses(){
        List<Bus> buses = null;
        try {
            buses = busDao.getAllBuses();
        } catch (BusNotFoundException e) {
            throw new RuntimeException(e);
        }
        return buses.stream().filter(bus -> bus.getAvailableSeats()>0).toList();
    }

//   8 Book seats
    public void bookSeats(int busId,int numberOfSeats) throws BusNotFoundException,BookingException{
        Bus bus = busDao.getBusById(busId);
        if (bus.getAvailableSeats()<numberOfSeats){
            throw new BookingException("Not enough seats available.");
        }
        bus.setAvailableSeats(bus.getAvailableSeats() - numberOfSeats);
        busDao.updateBus(bus);
    }

// 9   cancel booking
    public void cancelBooking(int busId,int numberOfSeats) throws BookingException,BusNotFoundException{
            Bus bus = busDao.getBusById(busId);
            if (numberOfSeats<0){
                throw new BookingException("No tickets left to cancel.");
            }
            bus.setAvailableSeats(bus.getAvailableSeats() + numberOfSeats);
            busDao.updateBus(bus);
    }

}
