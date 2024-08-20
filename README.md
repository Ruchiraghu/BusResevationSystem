# Bus Reservation System

## Introduction
The *Bus Reservation System* is a console-based application that allows users to book, manage, and cancel bus reservations. The system supports two user roles: *Admins* and *Customers*. It uses an SQL database to store and manage all the necessary data.

## Features

### For Admins
Admins manage the bus reservation system with the following capabilities:

#### Manage Buses
- *Add Bus*: Add new buses to the system.
- *Delete Bus*: Remove buses that are no longer in service.
- *Update Bus*: Modify details of existing buses.
- *Get Bus By ID*: View details of a specific bus using its ID.
- *Get All Buses*: View a list of all available buses.

#### Manage Admins
- *Update Admin*: Update admin account details.
- *Delete Admin*: Remove admin accounts.
- *Get Admin By ID*: View details of a specific admin using their ID.
- *Get All Admins*: View a list of all admins.

#### Manage Customers
- *Add Customer*: Add new customers to the system.
- *Update Customer*: Modify customer information.
- *Delete Customer*: Remove customer accounts.
- *Get Customer By ID*: View details of a specific customer using their ID.
- *Get All Customers*: View a list of all customers.

#### Logout
- *Logout*: Exit the admin account and return to the main menu.

### For Customers
Customers can manage their bus tickets with the following features:

#### Login
- *Enter Email*: Input your email address.
- *Enter Password*: Input your password.

#### Create Account
- *Enter Customer ID*: Provide a unique customer ID.
- *Enter Other Details*: Fill in additional customer details.

#### Book Tickets
- *Book a Bus Ticket*: Reserve seats on available buses.
- *View Bookings*: Check the details of your current bookings.
- *Cancel Bookings*: Cancel any of your bookings if needed.

#### View Buses
- *Search for Buses*: Find buses based on location or route.

#### Exit
- *Exit*: Close the application.

## Database
The system uses an SQL database to store and manage data, including:
- Bus details
- Customer information
- Bookings
- Admin accounts

The SQL script file is included in the repository to help set up the database.

## SQL Database Setup

1. *Import the SQL File*:
   - Locate the SQL script file in the repository.
   - Use your preferred SQL database management tool (e.g., MySQL Workbench) to import the file and set up the database.

2. *Configure the Database Connection*:
   - Update the database connection settings in your application to match your local setup (e.g., username, password, database name).

## How to Use It

### Admins:
- Log in to manage buses, admins, and customers.
- Use the admin menu options to add, update, or delete records.

### Customers:
- Create an account or log in if you already have one.
- Book tickets, view your bookings, or cancel them as needed.
- Use the menu options to find available buses based on your travel plans.

## Technology Behind It
- *Java*: The core programming language used to build the application.
- *SQL Database*: Stores and manages all data related to buses, customers, admins, and bookings.

## Getting Started

1. *Clone the Repository*:
   ```bash
   git clone https://github.com/yourusername/BusReservationSystem.git
2. *Set Up the SQL Database:*:
-Import the provided SQL script file into your SQL database.

3. *Open the Project::*:

Set Up the SQL Database:

Import the provided SQL script file into your SQL database.
Open the Project:

Open the project in your favorite Java IDE.
Run the Application:

Start the application and begin using the Bus Reservation System.
