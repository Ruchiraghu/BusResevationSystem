package test;

import dao.BusDao;
import daoImpl.BusDaoImpl;
import exception.BookingException;
import exception.BusNotFoundException;
import model.Bus;
import service.BusService;

import java.util.List;

public class BusServiceTest {
    private BusService busService;
    public BusDao busDao;

    public BusServiceTest() throws BusNotFoundException {
    }

    public void setUp() {
        busDao = new BusDaoImpl();
        busService = new BusService(busDao);
    }

    //    test 1-- add bus
    public void testAddBus() throws BusNotFoundException {
        setUp();
        Bus bus = new Bus(156,"Raghuwanshi","Non-AC",50,"8:00:00","9:00:00","Seoni-malwa to Narmadapuram");
        busService.addBus(bus);
        Bus retrieveBus = busService.getById(156);
        if (retrieveBus!=null&&retrieveBus.getBusName().equals("Raghuwanshi")){
            System.out.println("testAddBus passed.");
        }else{
            System.out.println("testAddBus failed!");
        }
    }

//   test 2-- get bus by id
    public void testGetBusById() throws BusNotFoundException {
        setUp();
        Bus bus = busService.getById(1);
        if (bus!=null&&bus.getBusId()==1){
            System.out.println("testGetBusById passed.");
        }else{
            System.out.println("testGetBusById failed!");
        }
    }
//    test 3-- get all buses
public void testGetAllBuses(){
        setUp();
    List<Bus> buses = busService.getAllBuses();
    if (buses!=null && !buses.isEmpty()){
        System.out.println("testGetAllBuses passed.");
    }else{
        System.out.println("testGetAllBuses failed.");
    }
    }
//     test 4-- bus updates
    public void testUpdateBus(){
        setUp();
        try {
            Bus bus = busService.getById(1);
            if (bus!= null){
                bus.setBusName("Updated bus");
                busService.updateBus(bus);

                Bus updatedBus = busService.getById(1);
                if (updatedBus.getBusName().equals("Updated bus")){
                    System.out.println("testUpdateBus passed.");
                }
                else{
                    System.out.println("testUpdateBus failed.");
                }
            }
        }catch (BusNotFoundException e) {
            System.out.println("testUpdateBus failed with exception");
            e.printStackTrace();
        }
    }
//    Test 5-- delete bus
    public void testDeleteBus(){
        setUp();
        try {
            busService.deleteBus(1);
            Bus bus = busService.getById(1);
            if (bus==null){
                System.out.println("testDeleteBus passed.");
            }else {
                System.out.println("testDeleteBus failed.");
            }
        } catch (BusNotFoundException e) {
            System.out.println("testDeleteBus failed with exception.");
            e.printStackTrace();
        }
    }
    // Test 6-- book seats
    public void testBookSeats() throws Exception {
        setUp();
        try {
            busService.bookSeats(1, 5);
            Bus bus = busService.getById(1);
            if (bus.getAvailableSeats() == 55) {
                System.out.println("testBookSeats passed.");
            } else {
                System.out.println("testBookSeats failed!");
            }
        } catch (BusNotFoundException | BookingException e) {
            System.out.println("testBookSeats failed with exception.");
            e.printStackTrace();
        }
    }

    // Test 7-- cancel booking
    public void testCancelBooking() {
        setUp();
        try {
            // Check initial bus state
            Bus busBeforeCancel = busService.getById(7);
            System.out.println("Available seats before cancellation: " + busBeforeCancel.getAvailableSeats());

            // Cancel the booking
            busService.cancelBooking(7, 1);

            // Check bus state after cancellation
            Bus busAfterCancel = busService.getById(7);
            System.out.println("Available seats after cancellation: " + busAfterCancel.getAvailableSeats());

            // Check the available seats have been correctly updated
            if (busAfterCancel.getAvailableSeats() == busBeforeCancel.getAvailableSeats() + 1) {
                System.out.println("testCancelBooking passed.");
            } else {
                System.out.println("testCancelBooking failed!");
            }
        } catch (BusNotFoundException | BookingException e) {
            System.out.println("testCancelBooking failed with exception.");
            e.printStackTrace();
        }
    }


    // Test 8-- get available buses
    public void testGetAvailableBuses() {
        setUp();
        List<Bus> buses = busService.getAvailableBuses();
        if (buses != null && !buses.isEmpty()) {
            System.out.println("testGetAvailableBuses passed.");
        } else {
            System.out.println("testGetAvailableBuses failed.");
        }
    }
    // Test 9-- validate bus data
    public void testValidateBus() throws BusNotFoundException {
        setUp();

        // Test case 1: Valid bus
        try {
            Bus validBus = new Bus(4, "Valid Bus", "AC", 40, "10:00:00", "14:00:00", "Route 1");
            busService.addBus(validBus);
            System.out.println("testValidateBus (valid data) passed.");
        } catch (IllegalArgumentException e) {
            System.out.println("testValidateBus (valid data) failed: " + e.getMessage());
        }

        // Test case 2: Invalid bus name
        try {
            Bus invalidBusName = new Bus(5, "", "Non-AC", 40, "10:00:00", "14:00:00", "Route 2");
            busService.addBus(invalidBusName);
            System.out.println("testValidateBus (invalid bus name) failed!");
        } catch (IllegalArgumentException e) {
            System.out.println("testValidateBus (invalid bus name) passed: " + e.getMessage());
        }

        // Test case 3: Negative available seats
        try {
            Bus invalidSeats = new Bus(6, "Invalid Seats Bus", "Non-AC", -5, "10:00:00", "14:00:00", "Route 3");
            busService.addBus(invalidSeats);
            System.out.println("testValidateBus (negative seats) failed!");
        } catch (IllegalArgumentException e) {
            System.out.println("testValidateBus (negative seats) passed: " + e.getMessage());
        }
    }



    public static void main(String[] args) throws BusNotFoundException {
        BusServiceTest test = new BusServiceTest();
//        test.testAddBus();
//        test.testGetBusById();
        test.testGetAllBuses();
//        test.testUpdateBus();
//        test.testDeleteBus();
//         test.testBookSeats();
//         test.testCancelBooking();
//         test.testGetAvailableBuses();
//        test.testValidateBus();
    }
}
