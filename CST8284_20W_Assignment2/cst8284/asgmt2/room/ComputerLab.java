package cst8284.asgmt2.room;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: CopmuterLab.java
	Date: February 20th, 2020
*/ 

public final class ComputerLab extends Room {
	private int seats = 30;
	
	
	public ComputerLab() {
		
	}
	@Override
	protected String getRoomType() {
		return "computer lab";
	}
	@Override
	protected int getSeats() {
		return seats;
	}
	@Override
	protected String getDetails() {
		return "countains outlets for 30 laptops";
	}
	
}
