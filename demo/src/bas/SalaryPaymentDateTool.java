package bas;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SalaryPaymentDateTool {

	public static void main(String[] args) throws IOException {
		System.out.println("Please enter the year");

		// Enter data using BufferReader
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		// Reading data using readLine
		String year = reader.readLine();

		// create an object and call non-static method to start process
		SalaryPaymentDateTool salaryPaymentDateTool = new SalaryPaymentDateTool();
		salaryPaymentDateTool.processSalaryBomus(year);

	}

	/**
	 * This will identify the salary and bonus pay out dates for 12 months of the
	 * given year
	 * 
	 * @param year
	 */
	private void processSalaryBomus(String year) {
		Integer inputYear = null;

		// This try catch block is used to validate the input is a valid number or not
		try {
			inputYear = Integer.parseInt(year);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		int startMonth = 1;

		// this list holds the header and all month details to save into the csv file
		List<String[]> finalResultList = new ArrayList<>();
		finalResultList.add(new String[] { "Month", "Salary Payout Date", "Bonus Payout Date" });
		LocalDate bonusDate = null;
		LocalDate salaryDate = null;

		// it will loop for 12 times to find the salary and bonus dates for all 12
		// months
		while (startMonth <= 12) {
			bonusDate = processBonus(startMonth, inputYear);
			salaryDate = processSalary(startMonth, inputYear);
			finalResultList.add(new String[] { Month.of(startMonth).toString(),
					salaryDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
					bonusDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) });
			startMonth++;
		}

		String fileName = "salary_payment_" + inputYear + ".csv";
		try {
			File csvOutputFile = new File(fileName);
			PrintWriter pw = new PrintWriter(csvOutputFile);
			finalResultList.stream().map(this::convertToCSV).forEach(pw::println);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Path path = Paths.get(fileName);
		System.out.println("The process completed. File is created at " + path.toAbsolutePath());
	}

	/**
	 * This function find the bonus pay out date. Consider 15th of the month if it
	 * is weekday Consider next Wednesday if the 15th of the month is weekend
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	private LocalDate processBonus(int month, int year) {
		int bonusPayDate = 15;

		// creating local date with given month and year. The bonus date should be 15 as
		// per requirement
		LocalDate bonusDate = LocalDate.of(year, month, bonusPayDate);
		if (isWeekend(bonusDate)) {
			// The TemporalAdjusters is the implementation class of the TemporalAdjuster
			// interface
			// The DayOfWeek is an enum. It holds seven predefined enum objects,
			// Monday-Sunday.
			// this will adjust the localdate to next Wednesday date and return modified
			// localdate.
			bonusDate = bonusDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
		}

		return bonusDate;
	}

	/**
	 * This function find the salary pay out date. This will return last day of the
	 * month if it is weekday. It will find last weekday of the month and return if
	 * the last day of the month is weekend
	 * 
	 * @param month
	 * @param year
	 * @return
	 */
	private LocalDate processSalary(int month, int year) {
		// creating salary date considering as 1st of the month
		LocalDate salaryDate = LocalDate.of(year, month, 1);

		// adjust the salary date with last day of the month using TemporalAdjusters
		// class
		salaryDate = salaryDate.with(TemporalAdjusters.lastDayOfMonth());

		if (isWeekend(salaryDate)) {

			// identifying the date is Saturday or Sunday
			int day = getDay(salaryDate);

			// getting the day from the salary date
			int salaryDay = salaryDate.getDayOfMonth();

			// find the last working day of the month
			// last working day always Friday if the last day of the month weekend
			// So subtract by 2 days if last day is Sunday or subtract by 1 day if the last
			// day is Saturday
			salaryDate = LocalDate.of(year, month, day == 7 ? salaryDay - 2 : salaryDay - 1);
		}

		return salaryDate;
	}

	private String convertToCSV(String[] data) {
		return Stream.of(data).collect(Collectors.joining(","));
	}

	/**
	 * This function will check the given localdate is weekend or not
	 * 
	 * @param localDate
	 * @return
	 */
	public boolean isWeekend(final LocalDate localDate) {
		DayOfWeek day = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
		return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
	}

	/**
	 * This function will return the int value of the day of the week
	 * 
	 * The values are numbered following the ISO-8601 standard, from 1 (Monday) to 7
	 * (Sunday)
	 * 
	 * @param localDate
	 * @return
	 */
	public int getDay(final LocalDate localDate) {
		DayOfWeek day = DayOfWeek.of(localDate.get(ChronoField.DAY_OF_WEEK));
		return day.getValue();
	}
}
