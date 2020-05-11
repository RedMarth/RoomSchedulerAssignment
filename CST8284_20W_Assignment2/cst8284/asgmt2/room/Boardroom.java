package cst8284.asgmt2.room;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: Boardroom.java
	Date: February 20th, 2020
*/ 
public final class Boardroom extends Room {
	private int seats = 16;
	
	public Boardroom() {
		
	}
	@Override
	protected String getRoomType() {
		return "board room";
	}
	@Override
	protected int getSeats() {
		return seats;
	}
	@Override
	protected String getDetails() {
		return "conference call enabled";
	}
	
}
