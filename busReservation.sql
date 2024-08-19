CREATE DATABASE bus_reservation_system;
use bus_reservation_system;
-- Bus Table 
create table buses(
bus_id INT PRIMARY KEY auto_increment,
bus_name VARCHAR(100) NOT NULL,
bus_type VARCHAR(100) NOT NULL,
available_seats INT NOT NULL,
departure_time TIME NOT NULL,
arrival_time TIME NOT NULL,
route VARCHAR(255) NOT NULL
);

-- customer table 
CREATE TABLE customers(
customer_id INT PRIMARY KEY auto_increment,
name VARCHAR(50)NOT NULL,
email VARCHAR(100)NOT NULL UNIQUE,
phone_number VARCHAR(15)NOT NULL);

-- RESERVATION TABLE
CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    bus_id INT NOT NULL,
    customer_id INT NOT NULL,
    reservation_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,   
    FOREIGN KEY (bus_id) REFERENCES buses(bus_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
 --  Admin table
 
 CREATE TABLE admin(
 admin_id INT PRIMARY KEY AUTO_INCREMENT,
 username VARCHAR(255)NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL
 );
 
  -- insert into buses
  INSERT INTO buses(bus_name,bus_type,available_seats,departure_time,arrival_time,route)
  VALUES
  ('Neeta Travels','AC',50,'09:00:00','01:30:00','Mumbai to Agra'),
  ('Shrinath Travels','AC',40,'12:00:00','10:00:00','Narmadapuram to Indore');
 
 -- INSERT INTO CUSTOMERS
 INSERT INTO customers(name,email,phone_number)
 VALUES
 ("Ruchi Raghuwanshi","ruchi25@gmail.com","123456789"),
 ("Priya Rao","raopriya1@gmail.com","9980892834");
 
 -- Insert reservation
 INSERT INTO reservations(bus_id,customer_id,reseravtion_date, status)
 VALUES
 (1,1,'12-08-2024','Booked'),
 (2,2,'11-09-2024','Booked');
 
 INSERT INTO admin(username,password)
 VALUES
 ('admin1','password1'),
 ('admin2','password')
 ;
 SELECT * FROM buses;


