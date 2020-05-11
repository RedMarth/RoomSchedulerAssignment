package cst8284.asgmt2.room;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: Room.java
	Date: February 20th, 2020
*/ 

public abstract class Room {
	
	private static final String DEFAULT_ROOM = "unknown room number";
	private String roomNumber;
	
	protected Room() {
		this(DEFAULT_ROOM);
	}
	
	protected Room(String roomNum) {
		setRoomNumber(roomNum); 
	}
	
	public void setRoomNumber(String roomNum) {roomNumber = roomNum;}
	public String getRoomNumber() {return roomNumber;}
	
    protected abstract String getRoomType() ;
	protected abstract int getSeats();
	protected abstract String getDetails();
	
	public String toString( ) { return getRoomNumber() + " is a " +
		getRoomType() + " with " + getSeats() + " seats; " + getDetails() +"\n" ;}
}
