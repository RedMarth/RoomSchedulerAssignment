package cst8284.asgmt2.roomScheduler;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: RoomScheduler.java
	Date: February 20th, 2020

	Based on code provided by Professor Dave Houtman for the solution to assignment 1.

*/ 

import cst8284.asgmt2.room.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.File;

public class RoomScheduler { 
	
	private static Scanner scan = new Scanner(System.in);
	private ArrayList<RoomBooking> roomBookings = new ArrayList<RoomBooking>();
	private Room room;
	
	private static final int DISPLAY_ROOM_INFORMATION = 1, ENTER_ROOM_BOOKING = 2, 
							DELETE_BOOKING = 3, CHANGE_BOOKING = 4, DISPLAY_BOOKING = 5,
							DISPLAY_DAY_BOOKINGS = 6, SAVE_BOOKINGS_TO_FILE = 7,
							LOAD_BOOKINGS_FROM_FILE = 8, EXIT = 0;
	
	public RoomScheduler(Room room) {
		setRoom(room);
	}
	
	/*Alvin Alexander. (2020). Java "file exists" testing. [Webpage] Retrieved from
	//https://alvinalexander.com/java/java-file-exists-directory-exists
	*/
	
	public void launch() {
		
		File checkExisting = new File("CurrentRoomBookings.book"); //checks to see if the file already exists, and loads the bookings if so.
		boolean exists = checkExisting.exists();
		
		if (exists == true) {
			loadBookingsFromFile();
		}
		
		int choice = 0;
		do {
		   choice = displayMenu();
		   executeMenuItem(choice);
		} while (choice != EXIT);		
	}
	
	private int displayMenu() {
		System.out.println("Enter a selection from the following menu:");
		System.out.println(
			DISPLAY_ROOM_INFORMATION + ". Display room information\n" +
			ENTER_ROOM_BOOKING + ". Enter a room booking\n" +
			DELETE_BOOKING + ". Remove a room booking\n" +
			CHANGE_BOOKING + ". Change a room booking\n" +
			DISPLAY_BOOKING  + ". Display a booking\n" +
			DISPLAY_DAY_BOOKINGS + ". Display room bookings for the whole day\n" +
			SAVE_BOOKINGS_TO_FILE + ". Backup current bookings to file\n" +
			LOAD_BOOKINGS_FROM_FILE + ". Load current bookings from file\n" +
			EXIT + ". Exit program");
		int ch = scan.nextInt();
		scan.nextLine();  // 'eat' the next line in the buffer
		System.out.println(); // add a space before next menu output
		return ch;
	}
	
	private void executeMenuItem(int choice) {
		switch (choice) {
			case DISPLAY_ROOM_INFORMATION:
				displayRoomInfo();
			break;
			case ENTER_ROOM_BOOKING: 
				saveRoomBooking(makeBookingFromUserInput()); 
				break;
			case DISPLAY_BOOKING: 
				displayBooking(makeCalendarFromUserInput(null, true));
				break;
			case DELETE_BOOKING:
				System.out.println("Enter room booking to delete ");
				deleteBooking(makeCalendarFromUserInput(null, true));
				//null because this makes the date entry prompt, true
				// because this makes the prompt for start time
				break;
			case CHANGE_BOOKING:
				System.out.println("Enter booking to change ");
				changeBooking(makeCalendarFromUserInput(null, true));
				break;			
			case DISPLAY_DAY_BOOKINGS: 
				displayDayBookings(makeCalendarFromUserInput(null, false)); 
				break;
			case SAVE_BOOKINGS_TO_FILE:
				saveBookingsToFile();
				System.out.println("Current room bookings backed up to file");
				break;
			case LOAD_BOOKINGS_FROM_FILE:
				loadBookingsFromFile();
				System.out.println("Current room bookings loaded from file");
				break;
			case EXIT: 
				System.out.println("Exiting Room Booking Application\n\n");
				saveBookingsToFile();
				break;
			default: System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println();  // add blank line after each output
	}
	
	private void displayRoomInfo() {
		System.out.println(room.toString());
	}
	
    private static String getResponseTo(String s) {
    	System.out.print(s);
		return(scan.nextLine());
    }
	
    private RoomBooking makeBookingFromUserInput() {	
    	String fullName = getResponseTo("Enter Client Name (as FirstName LastName): ");
    	String firstName = fullName.substring(0, fullName.indexOf(" "));
    	String lastName = fullName.substring(fullName.indexOf(" ")+1);
    	
    	ArrayList<String> nameList = new ArrayList<>();
    	
    	nameList.add(firstName);
    	nameList.add(lastName);
    	
    	String phoneNumber = getResponseTo("Phone Number (e.g. 613-555-1212): ");
		String organization = getResponseTo("Organization (optional): ");
		String category = getResponseTo("Enter event category: ");
		String description = getResponseTo("Enter detailed description of event: ");
		Calendar startCal = makeCalendarFromUserInput(null, true);
		Calendar endCal = makeCalendarFromUserInput(startCal, true);
		
		ContactInfo contactInfo = new ContactInfo(nameList.get(0), nameList.get(1), phoneNumber, organization);
		Activity activity = new Activity(category, description);
		TimeBlock timeBlock = new TimeBlock(startCal, endCal);
		return (new RoomBooking(contactInfo, activity, timeBlock));
    }
    
    /*JavaTutorialHQ. (2020).Java calendar set() method example. [Webpage]. Retrieved from
    https://javatutorialhq.com/java/util/calendar-class-tutorial/set-method-example/
    */
    
    //if entry calendar is null, user prompted for date. If requestHour is true,
    //user is prompted to enter start time.
    private Calendar makeCalendarFromUserInput(Calendar cal, boolean requestHour) {
    	Calendar calendar = Calendar.getInstance(); calendar.clear();
    	String date = "";
    	int hour = 0;	
    	boolean needCal = (cal==null);
    	
   		if (needCal) date = getResponseTo("Event Date (entered as DDMMYYYY): ");
   		int day = needCal ? Integer.parseInt(date.substring(0,2)) : cal.get(Calendar.DAY_OF_MONTH);
   		int month = needCal ? Integer.parseInt(date.substring(2,4))-1 : cal.get(Calendar.MONTH);
   		int year = needCal ? Integer.parseInt(date.substring(4,8)) : cal.get(Calendar.YEAR);
     		
		if (requestHour) {				
		   String time = getResponseTo((needCal?"Start":"End") +" Time: ");
		   hour = processTimeString(time);
		}
		//public final void set(int year, int month, int date, int hourOfDay, int minute)
		calendar.set(year, month, day, hour, 0);
		return (calendar);
    }
    
	private static int processTimeString(String t) {
		int hour = 0;
		t = t.trim();
		if (t.contains ("pm") || (t.contains("p.m."))) hour = Integer.parseInt(t.split(" ")[0]) + 12;
		if (t.contains("am") || t.contains("a.m.")) hour = Integer.parseInt(t.split(" ")[0]);
		if (t.contains(":")) hour = Integer.parseInt(t.split(":")[0]);
		return hour;
	}
	
    private RoomBooking findBooking(Calendar cal) {
    	Calendar oneHourLater = Calendar.getInstance();
    	oneHourLater.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY)+1);
    	TimeBlock findTB = new TimeBlock(cal, oneHourLater);    	
   
    	for (RoomBooking roomBooking: getRoomBookings())
    		if (roomBooking.getTimeBlock().overlaps(findTB) )
    			return roomBooking;
    		return null;
    }//end method
    	
	private boolean saveRoomBooking(RoomBooking booking) {	
		TimeBlock tb = booking.getTimeBlock();  // Check this TimeBlock to see if already booked
		Calendar cal = (Calendar)tb.getStartTime().clone(); // use its Calendar
		int hour = cal.get(Calendar.HOUR_OF_DAY);//Get first hour of block
		for (; hour < tb.getEndTime().get(Calendar.HOUR_OF_DAY); hour++){ //Loop through each hour in TimeBlock
			cal.set(Calendar.HOUR_OF_DAY, hour); // set next hour
		    if (findBooking(cal)!=null) {  // TimeBlock already booked at that hour, can't add appointment
		    	System.out.println("Cannot save booking; that time is already booked");
				return false;
		    }	
		}  // else time slot still available; continue loop to next hour
		
		getRoomBookings().add(booking);
		System.out.println("Booking time and date saved.");  
		return true;
	}
	
	private boolean deleteBooking(Calendar cal) {
		if (findBooking(cal) != null){
			displayBooking(cal);
			System.out.println("---------------\nPress 'Y' to confirm deletion, any other key to\nabort: ");
			String decision = scan.nextLine();
			if (decision.contains("Y")) {			
				getRoomBookings().remove(getRoomBookings().indexOf(findBooking(cal)));
				System.out.println("Booking deleted");
				return true;}
			else {
				return false;
				}
		}//end if
		return false;
	}//end method
	
	/*This code based on a Stack Overflow forum (2017). Retrieved from
	https://stackoverflow.com/questions/42612441/how-can-i-change-the-value-in-my-array-list-from-user-input#new-answer?newreg=46cf5af13fe049db9554bca2c0fda3ef
	*/
	
	private boolean changeBooking(Calendar cal) {
		Calendar newStartTime = Calendar.getInstance(); newStartTime.clear();
		Calendar newEndTime = Calendar.getInstance(); newEndTime.clear();			
		if (findBooking(cal) != null) {
			displayBooking(cal);
			for (int i = 0; i < getRoomBookings().size(); i++) {
				if (getRoomBookings().get(i).getTimeBlock().getStartTime().get(Calendar.HOUR_OF_DAY) == cal.get(Calendar.HOUR_OF_DAY)) { //compares hour of intaken calendar object from user input and all RoomBookings in the ArrayList. If there is a match, it then lets you edit the booking.
					String newDate = getResponseTo("Enter New Date (entered as DDMMYYYY): ");
					int day = Integer.parseInt(newDate.substring(0,2));
			   		int month = Integer.parseInt(newDate.substring(2,4))-1;
			   		int year = Integer.parseInt(newDate.substring(4,8));
					String startTime = getResponseTo("Enter New Start Time: ");
					String endTime = getResponseTo("Enter New End Time: ");
					int startHour = processTimeString(startTime);
					int endHour = processTimeString(endTime);
					
					//public final void set(int year, int month, int date, int hourOfDay, int minute)
					newStartTime.set(year, month, day, startHour, 0); //0 represents minute, etc in constructors
					newEndTime.set(year, month, day, endHour, 0);
					TimeBlock alteredTimeBlock = new TimeBlock(newStartTime, newEndTime);
					getRoomBookings().get(i).setTimeBlock(alteredTimeBlock);
					System.out.println("Booking has been changed to: \n---------------");
					displayBooking(getRoomBookings().get(i).getTimeBlock().getStartTime());
					return true;	
				}//end if
				else return false;
			}//end for
			return false;
		}else return false;
	}//end method
	
	private RoomBooking displayBooking(Calendar cal) {  
		RoomBooking booking = findBooking(cal);
		int hr = cal.get(Calendar.HOUR_OF_DAY);
		System.out.print((booking!=null) ?
		   "---------------\n"+ booking.toString()+"---------------\n": 
  	       "No booking scheduled between "+ hr + ":00 and " + (hr + 1) + ":00\n"
		);
		return booking;
	}
	
	private void displayDayBookings(Calendar cal) {
		for (int hrCtr = 8; hrCtr < 24; hrCtr++) {
			cal.set(Calendar.HOUR_OF_DAY, hrCtr);
			RoomBooking rb = displayBooking(cal);	
			if (rb !=null) hrCtr = rb.getTimeBlock().getEndTime().get(Calendar.HOUR_OF_DAY) - 1;
		}
	}
	
	/*Mykong. (2010). How to write to file in java - fileoutputstream. [Webpage]. Retrieved from
	https://mkyong.com/java/how-to-write-to-file-in-java-fileoutputstream-example/
	
	Oracle. (2018). Class eofexception. [Webpage]. Retrieved from 
	https://docs.oracle.com/javase/7/docs/api/java/io/EOFException.html
	
	Maneas, S. (2014). Java.io.notserializableexception - how to solve not serializable exception. [Webpage]. Retrieved from
	https://examples.javacodegeeks.com/java-basics/exceptions/java-io-notserializableexception-how-to-solve-not-serializable-exception/
	
	BeginWithJava. (2011). Java file save and fild load: objevts. [Webpage]. Retrieved from
	https://beginwithjava.blogspot.com/2011/04/java-file-save-and-file-load-objects.html
	*/
	
	private boolean saveBookingsToFile() {
		try(FileOutputStream objectFileStream = new FileOutputStream("CurrentRoomBookings.book");   //you create objects here because it ensures the input/output stream
			ObjectOutputStream oos = new ObjectOutputStream(objectFileStream); 						//will close properly. If they don't close properly, may get corrupt
		)																						    // files or data loss.
		{for (RoomBooking roomBooking: getRoomBookings())
			oos.writeObject(roomBooking); 
		}
		catch (IOException ex) {
			System.out.println("An I/O exception has occured.");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*This code based on a Stack Overflow forum (2016). Retrieved from
	https://stackoverflow.com/questions/35622101/t-cannot-be-resolved-to-a-type
	
	This code based on a Stack Overflow forum (2010). Retrieved from
	https://stackoverflow.com/questions/2592642/type-safety-unchecked-cast-from-object
	
	This code based on a Stack Overflow forum (2013). Retrieved from
	https://stackoverflow.com/questions/17636850/readobject-method-only-reads-the-first-object-it-receives?rq=1	
	*/
	
	private ArrayList<RoomBooking> loadBookingsFromFile() {
		getRoomBookings().clear();
		try(
			FileInputStream objectFileStream = new FileInputStream("CurrentRoomBookings.book");
			ObjectInputStream ois = new ObjectInputStream(objectFileStream);
		)	{while(true) {														//while (true) used if you do not know the number of objects, and keeps the connection open
				getRoomBookings().add((RoomBooking)ois.readObject());	
			}
		}
		catch (EOFException ex) {
		}
		catch (IOException ex2) {
		System.out.println("An I/O exception has occured.");
		ex2.printStackTrace();
		}
		catch (ClassNotFoundException ex3) {
			ex3.printStackTrace();
		}
		return getRoomBookings();
	}
	
	
	private ArrayList<RoomBooking> getRoomBookings() {
		return roomBookings;
	}
	
	private void setRoom(Room room) {
		this.room = room;
	}
	
	private Room getRoom() {
		return this.room;
	}
	  
}
