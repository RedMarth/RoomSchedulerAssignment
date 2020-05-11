package cst8284.asgmt2.room;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: Classroom.java
	Date: February 20th, 2020
*/ 

public final class Classroom extends Room {
	private int seats;
	
	public Classroom() {
		
	}
	
	@Override
	protected String getRoomType() {
		return "class room";
	}
	@Override
	protected int getSeats() {
		return seats;
	}
	@Override
	protected String getDetails() {
		return "contains overhead projector";
	}
	
}
