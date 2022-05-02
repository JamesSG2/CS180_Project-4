Test 1: User sign up
Steps:
1. User launches application.
2. User selects the "Sign up" button. 
3. User enters username via the keyboard.
4. User selects the "Ok" button.
5. User enters password via the keyboard.
6. User selects the "Ok" button.
7. User selects "Teacher" if they are a teacher and "Student" if they are a student
8. User selects "Ok" on confirmation window
Expected result: Application adds the user's username and password and sends user to log in.
Test Status: Passed. 

Test 2: User log in
Steps:
1. User launches application.
2. User selects the "log in" button. 
3. User enters username via the keyboard.
4. User selects the "Ok" button.
5. User enters password via the keyboard.
6. User selects the "Ok" button.
7. User enters which course they would like to access
8. User selects "Ok" on confirmation window
Expected result: Application verifies the user's username and password and sends user to student/teacher main menu. 
Test Status: Passed. 

Test 3: Teacher creates a quiz
Steps:
1. Teacher logs in (See test 2).
2. Teacher selects the "Create a quiz" option from the dropdown and selects the "Ok" button
3. Teacher enters the name of the quiz and selects the "Ok" button
4. Teacher enters the number of questions in the quiz and selects the "Ok" button
5. Teacher enters a question they would like on their quiz and selects the "Ok" button
6. Teacher enters option (a) and selects the "Ok" button
7. Teacher enters option (b) and selects the "Ok" button
8. Teacher enters option (c) and selects the "Ok" button
9. Teacher enters option (d) and selects the "Ok" button
10. Teacher either enters which option is correct or enters the word "file" if the response should be a file
11. Teacher selects the "Ok" button
12. Teacher enters how many points the question is worth and selects the "Ok" button
13. Teacher repeats steps 5-12 until the quiz is complete
14. Teacher selects "Ok" button on confirmation window
Expected result: Application adds the quiz to CourseData.txt and sends Teacher to main menu. 
Test Status: Passed. 

Test 4: Teacher edits a quiz
Steps:
1. Teacher logs in (See test 2).
2. Teacher selects the "Edit quiz" option from the dropdown and selects the "Ok" button
3. Teacher enters the name of the quiz they would like to edit and selects the "Ok" button
4. Teacher selects either the "Name" (step 5) or "Question" (steps 6-14) button depending on which they would like to change 
5. Teacher enters the new name of the quiz and selects the "Ok" button
6. Teacher enters which question number they would like to change
7. Teacher enters a new question they would like on their quiz and selects the "Ok" button
8. Teacher enters option (a) and selects the "Ok" button
9. Teacher enters option (b) and selects the "Ok" button
10. Teacher enters option (c) and selects the "Ok" button
11. Teacher enters option (d) and selects the "Ok" button
12. Teacher either enters which option is correct or enters the word "file" if the response should be a file
13. Teacher selects the "Ok" button
14. Teacher enters how many points the question is worth and selects the "Ok" button
15. Teacher selects "Ok" button on confirmation window
Expected result: Application edits the quiz in CourseData.txt and sends Teacher to main menu. 
Test Status: Passed. 

Test 5: Teacher deletes a quiz
Steps:
1. Teacher logs in (See test 2).
2. Teacher selects the "Delete quiz" option from the dropdown and selects the "Ok" button
3. Teacher enters the name of the quiz they would like to delete and selects the "Ok" button
4. Teacher selects "yes" if they would like to delete the quiz or "no" if they would not like it deleted
5. Teacher selects the "Ok" button on the confirmation window
Expected result: Application deletes the quiz from CourseData.txt and sends Teacher to main menu. 
Test Status: Passed. 

Test 6: Teacher uploads a quiz
Steps:
1. Teacher logs in (See test 2).
2. Teacher selects the "Upload quiz" option from the dropdown and selects the "Ok" button
3. Teacher selects the "Ok" button on the description window
4. Teacher enters the name of the file with the quiz and selects the "Ok" button
5. Teacher selects the "Ok" button on the confirmation window
Expected result: Application adds the quiz to CourseData.txt and sends Teacher to main menu. 
Test Status: Passed. 

Test 7: Teacher views a student submission
Steps:
1. Teacher logs in (See test 2).
2. Teacher selects the "View Submissions" option from the dropdown and selects the "Ok" button
3. Teacher selects the quiz they would like to see a submussion from and selects the "Ok" button
4. Teacher enters the username of the student whose submission they would like to view and selects the "Ok" button
5. Teacher enters the password of the student whose submission they would like to view and selects the "Ok" button
6. Teacher enters which submission they would like to see and selects the "Ok" button
Expected result: Application displays the student's quiz response with a timestamp of their submisson. 
Test Status: Passed. 

Test 8: User edits their account
Steps:
1. User logs in (See test 2).
2. User selects the "Edit Account" option from the dropdown and selects the "Ok" button
3. User enters new username via the keyboard.
4. User selects the "Ok" button.
5. User enters new password via the keyboard.
6. User selects the "Ok" button.
Expected result: Application edits the account information in AccountData.txt and sends user to their main menu. 
Test Status: Passed. 

Test 9: User deletes their account
Steps:
1. User logs in (See test 2).
2. User selects the "Delete Account" option from the dropdown and selects the "Ok" button
3. User selects the "Ok" button on the confirmation window
Expected result: Application deletes the account information in AccountData.txt and sends user to their main menu. 
Test Status: Passed. 

Test 10: Student takes a quiz
Steps:
1. Student logs in (See test 2).
2. Student selects the "Take a quiz" option from the dropdown and selects the "Ok" button
3. Student selects which quiz they would like to take from the dropdown and selects the "Ok" button
4. Student takes the quiz by selecting the option from the dropdown they think is correct and selecting the "Ok" button
5. Student selects "Yes" when prompted to submit the quiz
6. Student selects the "Ok" button on the confirmation window
Expected result: Application writes the student answers and results of the quiz to AccountData.txt and sends student to main menu. 
Test Status: Passed. 

Test 11: Student views their submission
Steps:
1. Student logs in (See test 2).
2. Student selects the "View submission" option from the dropdown and selects the "Ok" button
3. Student selects which quiz they would like to take from the dropdown and selects the "Ok" button
4. Student enters the attempt number they would like to see their score for and selects the "Ok" button
Expected result: Application displays the student's quiz response with a timestamp of their submisson. 
Test Status: Passed. 
