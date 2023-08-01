package com.example.hotelbooking;

import java.sql.SQLException;
import java.util.Scanner;

public interface BookingSystem {
    void bookRoom(Scanner scanner) throws SQLException;

    void checkRoomAvailability(Scanner scanner) throws SQLException;

    void makePayment(Scanner scanner) throws SQLException;

    void showRoomDetails(Scanner scanner) throws SQLException;

    void bookRoomByHotel(Scanner scanner) throws SQLException;

    void listAvailableRooms() throws SQLException;

    void showBookingDetails(Scanner scanner) throws SQLException;
}
