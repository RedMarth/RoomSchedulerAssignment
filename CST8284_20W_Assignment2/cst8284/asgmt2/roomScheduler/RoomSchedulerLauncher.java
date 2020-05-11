package cst8284.asgmt2.roomScheduler;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: RoomSchedulerLauncher.java
	Date: February 20th, 2020

	Based on code provided by Professor Dave Houtman for the solution to assignment 1.

*/ 

import cst8284.asgmt2.roomScheduler.RoomScheduler;
import cst8284.asgmt2.room.*;

public class RoomSchedulerLauncher {

	public static void main(String[] args) {
		
		ComputerLab room = new ComputerLab();
		room.setRoomNumber("B119");
		
		(new RoomScheduler(room)).launch();
	}
}
