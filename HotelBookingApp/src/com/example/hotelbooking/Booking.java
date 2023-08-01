package com.example.hotelbooking;

import java.sql.Date;

public class Booking {
    private int id;
    private int customerId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    public int getId() {
		return id;
	}
    @Override
    public String toString() {
        return "User ID: " + id +
               ", Room Id: " + roomId +
               ", Customer Id: " + customerId;
    }
	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	private double totalAmount;

    public Booking(int id, int customerId, int roomId, Date checkInDate, Date checkOutDate, double totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalAmount = totalAmount;
    }

     
}
