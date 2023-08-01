package com.example.hotelbooking;

public class Inherit {
	private int id;
    private String name;

    public Inherit(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println(id + ". " + name);

    }

    

	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
