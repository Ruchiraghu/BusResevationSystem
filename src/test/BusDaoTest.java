package test;

import daoImpl.BusDaoImpl;
import exception.BusNotFoundException;
import model.Bus;

import java.util.List;

public class BusDaoTest {

    private BusDaoImpl busDao;

    // Set up the test environment
    public void setUp() {
        busDao = new BusDaoImpl();
    }

    // Test--1 for getting a bus by ID
    public void testGetBusById() {
        setUp();
        try {
            Bus bus = busDao.getBusById(1);
            if (bus != null) {
                System.out.println("testGetBusById passed: Bus is not null.");
                System.out.println("Bus details: " + bus.getBusName() + ", " + bus.getBusType());
            } else {
                System.out.println("testGetBusById failed: Bus should not be null.");
            }
        } catch (BusNotFoundException e) {
            System.out.println("testGetBusById failed: BusNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test--2 for adding a bus
    public void testAddBus() {
        setUp();
        try {
            Bus newBus = new Bus(3, "New Bus", "AC", 40, "10:00:00", "12:00:00", "City A to City B");
            busDao.addBus(newBus);
            // Verify addition by fetching the bus
            Bus addedBus = busDao.getBusById(3);
            if (addedBus != null && "New Bus".equals(addedBus.getBusName())) {
                System.out.println("testAddBus passed: Bus added successfully.");
            } else {
                System.out.println("testAddBus failed: Bus was not added.");
            }
        } catch (Exception e) {
            System.out.println("testAddBus failed: Exception occurred.");
            e.printStackTrace();
        }
    }

    // Test--3 for updating a bus
    public void testUpdateBus() {
        setUp();
        try {
            Bus bus = busDao.getBusById(1);
            if (bus != null) {
                bus.setBusName("Foujdar");
                busDao.updateBus(bus);
                Bus updatedBus = busDao.getBusById(1);
                if (updatedBus != null && "Updated Bus".equals(updatedBus.getBusName())) {
                    System.out.println("testUpdateBus passed: Bus updated successfully.");
                } else {
                    System.out.println("testUpdateBus failed: Bus name not updated.");
                }
            } else {
                System.out.println("testUpdateBus failed: Bus not found.");
            }
        } catch (BusNotFoundException e) {
            System.out.println("testUpdateBus failed: BusNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test--4 for deleting a bus
    public void testDeleteBus() {
        setUp();
        try {
            busDao.deleteBus(2);
            Bus bus = busDao.getBusById(2);
            if (bus == null) {
                System.out.println("testDeleteBus passed: Bus deleted successfully.");
            } else {
                System.out.println("testDeleteBus failed: Bus still exists.");
            }
        } catch (Exception e) {
            System.out.println("testDeleteBus failed: Exception occurred.");
            e.printStackTrace();
        }
    }
    // Test--5 for getting all buses
    public void testGetAllBuses() {
        setUp();
        try {
            List<Bus> buses = busDao.getAllBuses();
            if (buses != null && !buses.isEmpty()) {
                System.out.println("testGetAllBuses passed: Buses retrieved successfully.");
                for (Bus bus : buses) {
                    System.out.println("Bus: " + bus.getBusName() + ", " + bus.getBusType());
                }
            } else {
                System.out.println("testGetAllBuses failed: No buses retrieved.");
            }
        } catch (BusNotFoundException e) {
            System.out.println("testGetAllBuses failed: BusNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BusDaoTest test = new BusDaoTest();
//        test.testGetBusById();
        test.testAddBus();
//        test.testUpdateBus();
//        test.testDeleteBus();
//        test.testGetAllBuses();
    }
}
