package service;

import dao.AdminDao;
import daoImpl.AdminDaoImpl;
import exception.AdminNotFoundException;
import exception.InvalidLoginException;
import model.Admin;

import java.util.List;

public class AdminService {
    private final AdminDao adminDao;

    public AdminService() {
        this.adminDao = new AdminDaoImpl();
    }

    // Authenticate admin
    public boolean authenticateAdmin(String username, String password) throws InvalidLoginException {
        try {
            Admin admin = adminDao.validateAdminCredentials(username, password);
           if (admin!=null){
               return true;
           }else{
               throw new InvalidLoginException("Invalid username or password!");

           }        } catch (InvalidLoginException e) {
            System.out.println("Authentication failed: "+e.getMessage());
            return false; // Return false if credentials are invalid
        }
    }

    // Get admin by ID
    public Admin getAdminById(int adminId) throws AdminNotFoundException {
        return adminDao.getAdminById(adminId);
    }

    // Get all admins
    public List<Admin> getAllAdmins() throws AdminNotFoundException {
        return adminDao.getAllAdmin();
    }

    // Add a new admin
    public void addAdmin(Admin admin) throws InvalidLoginException, AdminNotFoundException {
        adminDao.addAdmin(admin);
    }

    // Update existing admin
    public void updateAdmin(Admin admin) throws AdminNotFoundException {
        adminDao.updateAdmin(admin);
    }

    // Delete an admin
    public void deleteAdmin(int adminId) throws AdminNotFoundException {
        adminDao.deleteAdmin(adminId);
    }

    // Change admin password
    public void changeAdminPassword(int adminId, String newPassword) throws InvalidLoginException {
        adminDao.changeAdminPassword(adminId, newPassword);
    }

    public int getAdminCount() {
        return adminDao.getAdminCount();
    }
}
