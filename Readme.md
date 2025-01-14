# Terminal-Based System

This is a menu-driven system designed to manage three main types of users: 
Students, Professors, and Administrators. Each user type has specific roles and responsibilities within the system.
This system also features feedback submission and TA role.

This Readme specifically contains the addons of assignment 2.

***

## How to run code:
- Download folder
- Open intellij
- Change directory to the directory where you downloaded the folder to.
- Compile and run code.
- Read the terminal and input.

***

## Assumptions:
#### Feedback:
- All provided feedback is anonymous.
- Feedback can be submitted more than once.
- Feedback of a course can be read in the manage course for a professor.

#### Course Enrollment Limit:
- The professor or admin does not change the upper limit of the course after enrollment has started.

#### Drop Deadline:
- The drop deadline initially is the day the course is added.
- Professor and Admin both can update the drop deadline.

#### TA:
- The TA only assists in assigning grade to the students.
- The professor or admin knows which student he has to make TA and also the students username
- Both professor and admin can assign students as TA.
- Both professor and admin can remove TA's.

***

## Usage of Generic Programming, object class, and exception handling.

#### Generic Programming:
- The feedback class uses Generic Programming to get feedback in either integer or string form.
- Used wildcard to store course feedback.

#### Object Class:
- Throughout the code toString function is used that is in the object class.
- To compare strings .equals is used.

#### Exception Handling:
- Invalid Course Registration, Invalid Login, Course Drop Failure exceptions are handled.
- Throughout the code in various places try catch is used for invalid inputs.