package com.example.hotelbooking;

import java.sql.*;
import java.util.Scanner;

public class HotelBookingSystem implements BookingSystem{
    private Connection connection;

    public HotelBookingSystem(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void bookRoom(Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDateStr = scanner.nextLine();
        Date checkInDate = Date.valueOf(checkInDateStr);

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDateStr = scanner.nextLine();
        Date checkOutDate = Date.valueOf(checkOutDateStr);

        double totalAmount = calculateTotalAmount(roomId, checkInDate, checkOutDate);

        // Insert the booking details into the database
        String insertBookingQuery = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, total_amount) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookingQuery)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.setDate(3, checkInDate);
            preparedStatement.setDate(4, checkOutDate);
            preparedStatement.setDouble(5, totalAmount);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking successful. Total amount: $" + totalAmount);
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        }
    }
    @Override
    public void checkRoomAvailability(Scanner scanner) throws SQLException {
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();

        String checkAvailabilityQuery = "SELECT available FROM rooms WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkAvailabilityQuery)) {
            preparedStatement.setInt(1, roomId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boolean available = resultSet.getBoolean("available");
                    if (available) {
                        System.out.println("Room is available.");
                    } else {
                        System.out.println("Room is not available.");
                    }
                } else {
                    System.out.println("Invalid room ID.");
                }
            }
        }
    }
    @Override
    public void makePayment(Scanner scanner) throws SQLException {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        String getBookingQuery = "SELECT id, total_amount FROM bookings WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getBookingQuery)) {
            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int bookingId = resultSet.getInt("id");
                    double totalAmount = resultSet.getDouble("total_amount");

                    System.out.println("Booking ID: " + bookingId);
                    System.out.println("Total Amount: $" + totalAmount);

                    System.out.print("Enter payment amount: ");
                    double paymentAmount = scanner.nextDouble();

                    if (paymentAmount >= totalAmount) {
                        // Payment successful, update payment status in the database
                        String updatePaymentQuery = "UPDATE bookings SET paid = true WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updatePaymentQuery)) {
                            updateStatement.setInt(1, bookingId);
                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Payment successful.");
                            } else {
                                System.out.println("Payment update failed. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Insufficient payment amount.");
                    }
                } else {
                    System.out.println("Customer ID not found.");
                }
            }
        }
    }
    //@Override
    private double calculateTotalAmount(int roomId, Date checkInDate, Date checkOutDate) throws SQLException {
        // Code to calculate total amount based on room price and duration
        double totalAmount = 0.0;
        String getRoomPriceQuery = "SELECT price FROM rooms WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getRoomPriceQuery)) {
            preparedStatement.setInt(1, roomId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double roomPrice = resultSet.getDouble("price");
                    long durationInMillis = checkOutDate.getTime() - checkInDate.getTime();
                    int days = (int) (durationInMillis / (24 * 60 * 60 * 1000));
                    totalAmount = roomPrice * days;
                }
            }
        }
        return totalAmount;
    }
    @Override
    public void showRoomDetails(Scanner scanner) throws SQLException {
        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();

        String getRoomQuery = "SELECT id, type, capacity, price, available FROM rooms WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getRoomQuery)) {
            preparedStatement.setInt(1, roomId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    int capacity = resultSet.getInt("capacity");
                    double price = resultSet.getDouble("price");
                    boolean available = resultSet.getBoolean("available");

                    System.out.println("Room ID: " + id);
                    System.out.println("Type: " + type);
                    System.out.println("Capacity: " + capacity);
                    System.out.println("Price: $" + price);
                    System.out.println("Available: " + (available ? "Yes" : "No"));
                } else {
                    System.out.println("Room ID not found.");
                }
            }
        }
    }
    @Override
    public void bookRoomByHotel(Scanner scanner) throws SQLException {
        listHotels();

        System.out.print("Enter hotel ID: ");
        int hotelId = scanner.nextInt();
        scanner.nextLine();

        listAvailableRoomsByHotel(hotelId);

        System.out.print("Enter room ID: ");
        int roomId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInDateStr = scanner.nextLine();
        Date checkInDate = Date.valueOf(checkInDateStr);

        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutDateStr = scanner.nextLine();
        Date checkOutDate = Date.valueOf(checkOutDateStr);

        double totalAmount = calculateTotalAmount(roomId, checkInDate, checkOutDate);

        // Insert the booking details into the database
        String insertBookingQuery = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, total_amount) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertBookingQuery)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.setDate(3, checkInDate);
            preparedStatement.setDate(4, checkOutDate);
            preparedStatement.setDouble(5, totalAmount);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking successful. Total amount: $" + totalAmount);
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        }
    }
    
    private void listHotels() throws SQLException {
        String listHotelsQuery = "SELECT id, name FROM hotels";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(listHotelsQuery)) {
                System.out.println("Available Hotels:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                   // System.out.println(id + ". " + name);
                    Inherit hotel = new BasicHotel(id, name);
                }
            }
        }
    }
    
     
    private void listAvailableRoomsByHotel(int hotelId) throws SQLException {
        String listRoomsQuery = "SELECT id, type, capacity, price FROM rooms WHERE hotel_id = ? AND available = true";
        try (PreparedStatement preparedStatement = connection.prepareStatement(listRoomsQuery)) {
            preparedStatement.setInt(1, hotelId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Available Rooms in Hotel " + hotelId + ":");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    int capacity = resultSet.getInt("capacity");
                    double price = resultSet.getDouble("price");

                    System.out.println("Room ID: " + id);
                    System.out.println("Type: " + type);
                    System.out.println("Capacity: " + capacity);
                    System.out.println("Price: $" + price);
                    System.out.println("--------------------");
                }
            }
        }
    }
    @Override
    public void listAvailableRooms() throws SQLException {
        String listRoomsQuery = "SELECT id, type, capacity, price FROM rooms WHERE available = true";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(listRoomsQuery)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    int capacity = resultSet.getInt("capacity");
                    double price = resultSet.getDouble("price");

                    System.out.println("Room ID: " + id);
                    System.out.println("Type: " + type);
                    System.out.println("Capacity: " + capacity);
                    System.out.println("Price: $" + price);
                    System.out.println("--------------------");
                }
            }
        }
    }
    @Override
    public void showBookingDetails(Scanner scanner) throws SQLException {
        System.out.print("Enter booking ID: ");
        int bookingId = scanner.nextInt();

        String getBookingQuery = "SELECT id, customer_id, room_id, check_in_date, check_out_date, total_amount FROM bookings WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(getBookingQuery)) {
            preparedStatement.setInt(1, bookingId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int customerId = resultSet.getInt("customer_id");
                    int roomId = resultSet.getInt("room_id");
                    Date checkInDate = resultSet.getDate("check_in_date");
                    Date checkOutDate = resultSet.getDate("check_out_date");
                    double totalAmount = resultSet.getDouble("total_amount");

                    System.out.println("Booking ID: " + id);
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Room ID: " + roomId);
                    System.out.println("Check-in Date: " + checkInDate);
                    System.out.println("Check-out Date: " + checkOutDate);
                    System.out.println("Total Amount: $" + totalAmount);
                } else {
                    System.out.println("Booking ID not found.");
                }
            }
        }
    }

 }

