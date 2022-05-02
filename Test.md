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

Test 2: User log up
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
