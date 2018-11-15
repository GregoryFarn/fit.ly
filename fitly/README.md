CSCI 310
Team 6: fit.ly

Instructions:
To run the app launch it on your android device with SDK 15 or later.

Improvments of the Android application since v2.3:

1) Users can now log in and sign up with valid credentials. If credentials are invalid, we can display an error message and prevent them from accessing the main page.

<<<<<<< HEAD
2) Implemented the connection with our Firebase database and moved all of our user data there.

3) We implemented a core functionality, the calorie tracker. There is now a button on the Dashboard page that navigates user to an Add Calorie Consumption page, similar to our Add Workout page. The calorie tracker also works with our step count, and integrated the calorie tracker with the service so that it logs calories while the user is inactive.

4) We integrated Firebase with the service. Our service can now read and write to the database even while the user is inactive. 

5) We added a checkbox next to every workout on the schedule page that allows users to mark workouts as completed. Upon completion, an intent is sent to the service to update our calorie count, and also updates the database.

6) At midnight, an ActivityReport object is created with all the data required for that day. This was especially important for our alarm notifications which have to go off three hours before the planned workout time.

7) Our Workout class has been restructured - our startTime and endTime Calendar objects were converted into String objects so we can store them in the database. 
=======
2) Moved all of our data to Firebase and implemented the connection with our Firebase database. 

3) We implemented a core functionality, the calorie tracker. There is now a button on the Dashboard page that navigates user to an Add Calorie Consumption page, similar to our Add Workout page. The calorie tracker also works with our step count, and integrated the calorie tracker with the service so that it logs calories while the user is inactive.

4) We also integrated Firebase with the service. This is used for marking the workouts as completed and for our badge completion. 
UI for marking workouts for completed is functional.
>>>>>>> f71eb930e8aabdf1a803aa64bec50580d957553f
