package com.example.hotelbooking;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "nithin@792");
            HotelBookingSystem hotelBookingSystem = new HotelBookingSystem(connection);

            while (true) {
                System.out.println("1. Book a room");
                System.out.println("2. Check room availability");
                System.out.println("3. Make payment");
                System.out.println("4. Show room details");
                System.out.println("5. List available rooms");
                System.out.println("6. Show booking details");
                System.out.println("7. Book a room by choosing hotel and room");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        hotelBookingSystem.bookRoom(scanner);
                        break;
                    case 2:
                        hotelBookingSystem.checkRoomAvailability(scanner);
                        break;
                    case 3:
                        hotelBookingSystem.makePayment(scanner);
                        break;
                    case 4:
                        hotelBookingSystem.showRoomDetails(scanner);
                        break;
                    case 5:
                        hotelBookingSystem.listAvailableRooms();
                        break;
                    case 6:
                        hotelBookingSystem.showBookingDetails(scanner);
                        break;
                    case 7:
                        hotelBookingSystem.bookRoomByHotel(scanner);
                        break;
                    case 8:
                        connection.close();
                        System.out.println("Thank you for using the hotel booking system. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
