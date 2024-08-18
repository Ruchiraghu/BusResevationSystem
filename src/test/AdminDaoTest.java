package test;

import daoImpl.AdminDaoImpl;
import exception.AdminNotFoundException;
import exception.InvalidLoginException;
import model.Admin;

import java.util.List;

public class AdminDaoTest {

    private AdminDaoImpl adminDao;

    // Set up the test environment
    public void setUp() {
        adminDao = new AdminDaoImpl();
    }

    // Test 1: Getting an admin by ID
    public void testGetAdminById() {
        setUp();
        try {
            Admin admin = adminDao.getAdminById(1);
            if (admin != null) {
                System.out.println("testGetAdminById passed: Admin is not null.");
                System.out.println("Admin details: " + admin.getUsername());
            } else {
                System.out.println("testGetAdminById failed: Admin should not be null.");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testGetAdminById failed: AdminNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test 2: Adding an admin
    public void testAddAdmin() {
        setUp();
        try {
            Admin newAdmin = new Admin();
            newAdmin.setAdminId(1);
            newAdmin.setUsername("newAdmin");
            newAdmin.setPassword("newPassword");
            adminDao.addAdmin(newAdmin);
            // Verify addition by fetching the admin
            Admin addedAdmin = adminDao.getAdminById(newAdmin.getAdminId());
            if (addedAdmin != null && "newAdmin".equals(addedAdmin.getUsername())) {
                System.out.println("testAddAdmin passed: Admin added successfully.");
            } else {
                System.out.println("testAddAdmin failed: Admin was not added.");
            }
        } catch (InvalidLoginException e) {
            System.out.println("testAddAdmin failed: InvalidLoginException was thrown.");
            e.printStackTrace();
        } catch (AdminNotFoundException e) {
            System.out.println("testAddAdmin failed: AdminNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test 3: Updating an admin
    public void testUpdateAdmin() {
        setUp();
        try {
            Admin admin = adminDao.getAdminById(1);
            if (admin != null) {
                admin.setUsername("updatedAdmin");
                admin.setPassword("updatedPassword");
                adminDao.updateAdmin(admin);
                Admin updatedAdmin = adminDao.getAdminById(1);
                if (updatedAdmin != null && "updatedAdmin".equals(updatedAdmin.getUsername())) {
                    System.out.println("testUpdateAdmin passed: Admin updated successfully.");
                } else {
                    System.out.println("testUpdateAdmin failed: Admin was not updated.");
                }
            } else {
                System.out.println("testUpdateAdmin failed: Admin not found.");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testUpdateAdmin failed: AdminNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test 4: Deleting an admin
    // Test 4: Deleting an admin
    public void testDeleteAdmin() {
        setUp();
        try {
            // Verify that the admin exists before deletion
            Admin existingAdmin = adminDao.getAdminById(1);
            if (existingAdmin == null) {
                System.out.println("testDeleteAdmin failed: Admin with ID 2 does not exist.");
                return;
            }

            // Proceed with deletion
            adminDao.deleteAdmin(1);

            // Verify that the admin has been deleted
            try {
                adminDao.getAdminById(1);
                System.out.println("testDeleteAdmin failed: Admin was not deleted.");
            } catch (AdminNotFoundException e) {
                System.out.println("testDeleteAdmin passed: Admin was successfully deleted and not found.");
            }
        } catch (Exception e) {
            System.out.println("testDeleteAdmin failed: Exception occurred.");
            e.printStackTrace();
        }
    }

    // Test 5: Getting all admins
    public void testGetAllAdmins() {
        setUp();
        try {
            List<Admin> admins = adminDao.getAllAdmin();
            if (admins != null && !admins.isEmpty()) {
                System.out.println("testGetAllAdmins passed: Admins retrieved successfully.");
                for (Admin admin : admins) {
                    System.out.println("Admin: " + admin.getUsername());
                }
            } else {
                System.out.println("testGetAllAdmins failed: No admins retrieved.");
            }
        } catch (AdminNotFoundException e) {
            System.out.println("testGetAllAdmins failed: AdminNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    // Test 6: Validating admin credentials
    public void testValidateAdminCredentials() {
        setUp();
        try {
            Admin isValid = adminDao.validateAdminCredentials("newAdmin", "newPassword");
            System.out.println("testValidateAdminCredentials: Credentials are valid: " + isValid);
        } catch (InvalidLoginException e) {
            System.out.println("testValidateAdminCredentials failed: InvalidLoginException was thrown.");
            e.printStackTrace();
        }
    }

    // Test 7: Changing an admins password
    public void testChangeAdminPassword() {
        setUp();
        try {
            String newUsername = "updatedAdmin"; // Ensure this is the correct username for the test
            String newPassword = "newUpdatedPassword";

            // Fetch the existing admin
            Admin admin = adminDao.getAdminById(1);
            if (admin == null) {
                System.out.println("testChangeAdminPassword failed: Admin with ID 1 does not exist.");
                return;
            }

            // Update the admin's username and password
            admin.setUsername(newUsername);
            admin.setPassword(newPassword);
            adminDao.updateAdmin(admin);

            // Verify by attempting login with new username and password
            Admin retrievedAdmin = adminDao.validateAdminCredentials(newUsername, newPassword);
            if (retrievedAdmin != null && newUsername.equals(retrievedAdmin.getUsername())) {
                System.out.println("testChangeAdminPassword passed: Password changed successfully.");
            } else {
                System.out.println("testChangeAdminPassword failed: Password change was not successful.");
            }
        } catch (InvalidLoginException e) {
            System.out.println("testChangeAdminPassword failed: InvalidLoginException was thrown.");
            e.printStackTrace();
        } catch (AdminNotFoundException e) {
            System.out.println("testChangeAdminPassword failed: AdminNotFoundException was thrown.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdminDaoTest test = new AdminDaoTest();
//        test.testGetAdminById();
//        test.testAddAdmin();
//        test.testUpdateAdmin();
//        test.testDeleteAdmin();
//        test.testGetAllAdmins();
//        test.testValidateAdminCredentials();
        test.testChangeAdminPassword();
    }
}
