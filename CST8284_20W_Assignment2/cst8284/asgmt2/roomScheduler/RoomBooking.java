package cst8284.asgmt2.roomScheduler;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: RoomBooking.java
	Date: February 20th, 2020

	Based on code provided by Professor Dave Houtman for the solution to assignment 1.

*/ 
import cst8284.asgmt2.roomScheduler.Activity;
import cst8284.asgmt2.roomScheduler.ContactInfo;
import cst8284.asgmt2.roomScheduler.TimeBlock;
import java.io.Serializable;
public class RoomBooking implements Serializable{
	private ContactInfo contactInfo;
	private Activity activity;
	private TimeBlock timeBlock;
	
	public static final long serialVersionUID = 1L;
	
	public RoomBooking(ContactInfo contactInfo, Activity activity, TimeBlock timeBlock) {
		setContactInfo(contactInfo); setActivity(activity); setTimeBlock(timeBlock);
	}
	
	public RoomBooking() {}

	public void setContactInfo(ContactInfo contactInfo) {this.contactInfo = contactInfo;}
	public ContactInfo getContactInfo() {return contactInfo;}

	public void setActivity(Activity activity) {this.activity = activity;}
	public Activity getActivity() {return activity;}

	public void setTimeBlock(TimeBlock timeBlock) {this.timeBlock = timeBlock;}
	public TimeBlock getTimeBlock() {return timeBlock;}

	@Override public String toString() {
		return (getTimeBlock().toString() + getActivity().toString() +  getContactInfo().toString());
	}

}
