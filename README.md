# SchoolWork
This assignment was given for us to practice with ArrayLists, as well as file I/O in Java.
The idea was to create a room scheduler which allows the user to book a room, and checks for conflicts
with existing bookings. The user can also view information of, modify, or delete a booking.
Existing RoomBooking objects can be pulled one by one from the ArrayList and saved to a file. 
The program automatically saves the bookings to a file when exit is selected. If a file exists
containing an existing schedule, it is automatically loaded when the program begins.

1. Display room information - Displays information regarding the type of room. As it stands, the program is hard coded to schedule
for a computer lab.

2. Enter a room booking - The user is prompted for a name, phone number, and organization, event category, description of event, date,
start time, and end time. If an existing booking exists which overlaps with the desired time slot, the entry is denied. 
Otherwise, it is added to the ArrayList.

3. Remove a room booking - The user is prompted for a date and start time. If a matching booking exists, the information for the room is displayed
and the user is asked if they wish to delete. If yes, then the booking is removed from the ArrayList.

4. Change a room booking -  The user is prompted for a date and start time. If a matching booking exists, the information for the room is displayed
and the user is asked to enter a new date and time. It turns out there is a bug in here that I missed during my testing, so this method is not
functioning properly.

5. Display a booking - The user is prompted for a date and start time. If a matching booking exist, the information is displayed.

6. Display room bookings for the whole day - The user is prompted for a date. All empty time slots will be displayed for that day in 1 hour blocks.

7. Backup current bookings to file - a file is created to store the RoomBooking objects.

8. Load current bookings from file - if a file exists containing RoomBooking objects, they will be loaded into the ArrayList.

0. Exit program - automatically saves RoomBooking objects to a file, which is then used to load the ArrayList when the program is run again.
