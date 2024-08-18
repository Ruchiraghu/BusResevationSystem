package test;

import exception.AdminNotFoundException;
import exception.InvalidLoginException;
import model.Admin;
import service.AdminService;

import java.util.List;

public class AdminServiceTest {
    private AdminService adminService;

    public void setUp() {
        adminService = new AdminService();
    }

    // Test 1-- add admin
    public void testAddAdmin() {
        setUp();
        try {
            Admin admin = new Admin(3, "newAdmin", "password123");
            adminService.addAdmin(admin);

            Admin retrievedAdmin = adminService.getAdminById(3);
            if (retrievedAdmin != null && retrievedAdmin.getUsername().equals("newAdmin")) {
                System.out.println("testAddAdmin passed.");
            } else {
                System.out.println("testAddAdmin failed!");
            }
        } catch (InvalidLoginException | AdminNotFoundException e) {
            System.out.println("testAddAdmin failed with exception.");
            e.printStackTrace();
        }
    }

    // Test 2-- get admin by id
    public void testGetAdminById() {
        setUp();
        try {
            Admin admin = adminService.getAdminById(1);
            if (admin != null && admin.getAdminId() == 1) {
                System.out.println("testGetAdminById passed.");
            } else {
                System.out.println("testGetAdminById failed!");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testGetAdminById failed with exception.");
            e.printStackTrace();
        }
    }

    // Test 3-- get all admins
    public void testGetAllAdmins() {
        setUp();
        try {
            List<Admin> admins = adminService.getAllAdmins();
            if (admins != null && !admins.isEmpty()) {
                System.out.println("testGetAllAdmins passed.");
            } else {
                System.out.println("testGetAllAdmins failed!");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testGetAllAdmins failed with exception.");
            e.printStackTrace();
        }
    }

    // Test 4-- update admin
    public void testUpdateAdmin() {
        setUp();
        try {
            Admin admin = adminService.getAdminById(1);
            if (admin != null) {
                admin.setUsername("updatedAdmin");
                adminService.updateAdmin(admin);

                Admin updatedAdmin = adminService.getAdminById(1);
                if (updatedAdmin.getUsername().equals("updatedAdmin")) {
                    System.out.println("testUpdateAdmin passed.");
                } else {
                    System.out.println("testUpdateAdmin failed!");
                }
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testUpdateAdmin failed with exception.");
            e.printStackTrace();
        }
    }

    // Test 5-- delete admin
    public void testDeleteAdmin() {
        setUp();
        try {
            // First, verify that the admin exists
            Admin admin = adminService.getAdminById(2);
            if (admin != null) {
                // Proceed to delete the admin
                adminService.deleteAdmin(2);

                // Try to retrieve the deleted admin
                try {
                    adminService.getAdminById(2);
                    System.out.println("testDeleteAdmin failed! Admin was not deleted.");
                } catch (AdminNotFoundException e) {
                    // Expected outcome: admin should not be found
                    System.out.println("testDeleteAdmin passed.");
                }
            } else {
                System.out.println("Admin with ID 2 does not exist for the test.");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testDeleteAdmin failed with exception.");
            e.printStackTrace();
        }
    }

//6-- authentication test
// Test 6-- authentication test
public void testAuthenticateAdmin() {
    try {
        // Attempt to authenticate with the provided username and password
        boolean isAuthenticated = adminService.authenticateAdmin("newAdmin", "password123");

        // Check if authentication was successful
        if (isAuthenticated) {
            System.out.println("testAuthenticateAdmin passed.");
        } else {
            System.out.println("testAuthenticateAdmin failed: Authentication was not successful.");
        }
    } catch (InvalidLoginException e) {
        System.out.println("testAuthenticateAdmin failed with exception.");
        e.printStackTrace();
    }
}


    // Test 7-- change admin password
    public void testChangeAdminPassword() {
        setUp();
        try {
            // Define a new password and change it
            String newPassword = "newPassword123";
            adminService.changeAdminPassword(1, newPassword);

            // Retrieve the admin and verify the password change
            Admin updatedAdmin = adminService.getAdminById(1);

            // Attempt to authenticate with the new password
            boolean isAuthenticated = adminService.authenticateAdmin(updatedAdmin.getUsername(), newPassword);

            if (isAuthenticated) {
                System.out.println("testChangeAdminPassword passed.");
            } else {
                System.out.println("testChangeAdminPassword failed!");
            }
        } catch (InvalidLoginException | AdminNotFoundException e) {
            System.out.println("testChangeAdminPassword failed with exception.");
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        AdminServiceTest test = new AdminServiceTest();
//        test.testAddAdmin();
//        test.testGetAdminById();
//        test.testGetAllAdmins();
//        test.testUpdateAdmin();
//        test.testDeleteAdmin();
//        test.testAuthenticateAdmin();
        test.testChangeAdminPassword();
    }
}
