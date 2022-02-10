# Salary Payment Date tool

**Requirements:**
This company is handling their sales payroll in the following way: 
- Sales staff gets a monthly fixed base salary and a monthly bonus.  
- The base salaries are paid on the last day of the month unless that day is a  Saturday or a Sunday (weekend).  
- On the 15th of every month bonuses are paid for the previous month, unless  that day is a weekend. In that case, they are paid the first Wednesday after the 15th.  
- The output of the utility should be a CSV file, containing the payment dates for the remainder of this year. The CSV file should contain a column for the month name, a column that contains the salary payment date for that month, and a column that contains the bonus payment date

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing.

### Prerequisities

- [JDK 1.8+](https://www.oracle.com/java/technologies/downloads/)

### Installing

Download the code using the "Download Zip" option. Then extract it

## Compile the code

Open the terminal with the src folder and run below command to compile
```
javac bas/SalaryPaymentDateTool.java
```

## Run the compiled code

To see the output CSV file follow the steps mentioned here

Step 1: Run the below command to start the program
```
java bas/SalaryPaymentDateTool
```
Step 2: It will prompt you to enter the year as an input. Example 2023, 2024 etc

Step 3: Hit enter after providing the input. You will see the success message once the process completes.

Step 4: You can find CSV file in the same src folder
