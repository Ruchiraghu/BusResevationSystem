package daoImpl;

import dao.CustomerDao;
import exception.CustomerNotFound;
import model.Customer;
import utility.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private Connection connection;
    Customer customer = null;
    PreparedStatement ppst = null;
    ResultSet rs = null;
   public CustomerDaoImpl(){
        this.connection = DBConnection.getConnection();
    }


    //    1 get customer id
    @Override
    public Customer getCustomerId(int customerId)throws CustomerNotFound {
        String sql = "SELECT * FROM customers WHERE customer_id=?";
        try(PreparedStatement ppst = connection.prepareStatement(sql)){
            ppst.setInt(1,customerId);
            rs = ppst.executeQuery();
            if (rs.next()){
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setPassword(rs.getString("password"));
            }else{
                throw new CustomerNotFound("Customer with ID: "+customerId+" not found.");
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new CustomerNotFound();
        }
        return customer;
    }

//  2  get all customers
    @Override
    public List<Customer> getAllCustomer() throws CustomerNotFound {
       List<Customer> customers = new ArrayList<>();
       String sql = "SELECT * FROM customers";
       try(PreparedStatement ppst = connection.prepareStatement(sql)){
           rs= ppst.executeQuery();
           while (rs.next()){
               customer = new Customer();
               customer.setCustomerId(rs.getInt("customer_id"));
               customer.setName(rs.getString("name"));
               customer.setEmail(rs.getString("email"));
               customer.setPhoneNumber(rs.getString("phone_number"));
               customer.setPassword(rs.getString("password"));
               customers.add(customer);
           }
       }catch (SQLException e){
           e.printStackTrace();
           throw new CustomerNotFound(e.getMessage());

       }
        return customers;
    }

//3  update customers details
    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFound{
       String sql = "UPDATE customers SET name=?,email=?,phone_number=?,password=? WHERE customer_id=?";
       try(PreparedStatement ppst = connection.prepareStatement(sql)){
           ppst.setString(1, customer.getName());
           ppst.setString(2, customer.getEmail());
           ppst.setString(3, customer.getPhoneNumber());
           ppst.setString(4, customer.getPassword());
           ppst.setInt(5, customer.getCustomerId());


          int rowAffected = ppst.executeUpdate();
          if(rowAffected>0){
              System.out.println("Customer is updates successfully.");
          }else{
              System.out.println("Failed! to update customer.");
          }
       }catch (SQLException e){
           throw new CustomerNotFound(e.getMessage());
       }
    }

// 4 delete customer
    @Override
    public void deleteCustomer(int customerId)throws CustomerNotFound {
       String sql = "DELETE FROM customers WHERE customer_id=?";
       try(PreparedStatement ppst = connection.prepareStatement(sql)){
           ppst.setInt(1,customerId);
           int rowAffected = ppst.executeUpdate();

           if (rowAffected==0){
               throw new CustomerNotFound("Customer with ID: " + customerId + " not found.");
           }
       }
       catch (SQLException e){
           e.printStackTrace();
           throw new CustomerNotFound("Throwing exception");
       }
    }

// 5 get customer by email
    @Override
    public Customer getCustomerByEmail(String email)throws CustomerNotFound {
       String sql = "SELECT * FROM customers WHERE email=?";
       try (PreparedStatement ppst = connection.prepareStatement(sql)){
           ppst.setString(1,email);
           rs = ppst.executeQuery();
           if (rs.next()){
               customer = new Customer();
               customer.setCustomerId(rs.getInt("customer_id"));
               customer.setName(rs.getString("name"));
               customer.setEmail(rs.getString("email"));
               customer.setPhoneNumber(rs.getString("phone_number"));
               customer.setPassword(rs.getString("password"));
           }
       }catch (SQLException e){
           e.printStackTrace();
           throw new CustomerNotFound("Customer not found.");
       }
        return customer;
    }

//    6 add customer
    @Override
    public void addCustomer(Customer customer)throws CustomerNotFound {
       String sql = "INSERT INTO customers (customer_id,name,email,phone_number,password) VALUES(?,?,?,?,?)";

       try (PreparedStatement ppst = connection.prepareStatement(sql)){
           ppst.setInt(1,customer.getCustomerId());
           ppst.setString(2, customer.getName());
           ppst.setString(3, customer.getEmail());
           ppst.setString(4, customer.getPhoneNumber());
           ppst.setString(5,customer.getPassword());
           int rowAffected = ppst.executeUpdate();
           if (rowAffected>0){
               System.out.println("Customer added successfully.");
           }else{
               System.out.println("Unable to add customer!");
           }
       }catch (SQLException e){
           e.printStackTrace();
           throw new CustomerNotFound("Customer not found.");
       }
    }
}
