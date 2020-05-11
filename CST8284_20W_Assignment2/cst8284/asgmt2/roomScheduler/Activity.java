package cst8284.asgmt2.roomScheduler;
/*  Course Name: CST8284
	Student name: Jonathan Cordingley
	Class name: Activity.java
	Date: February 20th, 2020

	Based on code provided by Professor Dave Houtman for the solution to assignment 1.

*/ 

import java.io.Serializable;

public class Activity implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	private String category, description;
	
	public Activity(String category, String description) {
		setCategory(category); setDescription(description);	
	}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public String getCategory() {return category;}
	public void setCategory(String category) {this.category = category;}
	
	@Override
	public String toString() {
		return  "Event: " + getCategory() + "\n" + 
			((getDescription()!="")?"Description: " + getDescription():"") + "\n";
	}
}
