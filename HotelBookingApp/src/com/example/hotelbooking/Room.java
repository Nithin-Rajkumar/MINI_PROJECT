package com.example.hotelbooking;

public class Room {
    private int id;
    private String type;
    private int capacity;
    private int hotelId;  
    private double price;
    private boolean available;
    //@Override
    public Room(int id, String type, int capacity, double price, boolean available, int hotelId) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.available = available;
        this.hotelId = hotelId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	

     

     
}

