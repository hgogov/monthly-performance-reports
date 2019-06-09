package monthlyreports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.core.type.TypeReference;

public class Program {

	public static float calculateScore(Employee employee, ReportDefinition report) {
		float score = 0;
		try {
			if (report.isUseExperienceMultiplier()) {
				score = employee.getTotalSales() / employee.getSalesPeriod() * employee.getExperienceMultiplier();
			} else {
				score = employee.getTotalSales() / employee.getSalesPeriod();
			}
		} catch (ArithmeticException e) {
			System.out.println("Division by zero error. " + employee.getName() + " sales period is zero.");
		}
		return score;
	}

	public static boolean isEmployeeAcceptedForReport(Employee employee, ReportDefinition report, float score) {
		if (employee.getSalesPeriod() <= report.getPeriodLimit() && score >= report.getTopPerformersThreshold())
			return true;

		return false;
	}

	public static void generateCSVReport(String file, Employee[] employees, ReportDefinition report) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.println("Name,Score");
			for (Employee employee : employees) {
				float score = 0;
				score = calculateScore(employee, report);
				if (isEmployeeAcceptedForReport(employee, report, score)) {
					writer.println(employee.getName() + ',' + score);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			writer.flush();
			writer.close();
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Employee[] employees = mapper.readValue(new File(args[0]), new TypeReference<Employee[]>() {
				});
				ReportDefinition reportDefinition = mapper.readValue(new File(args[1]), ReportDefinition.class);
				generateCSVReport("monthly_performance_results_" + LocalDate.now().getYear() + "_"
						+ LocalDate.now().getMonth() + ".csv", employees, reportDefinition);
			} catch (MismatchedInputException e) {
				System.out.println("Incorrect json format.");
			} catch (NullPointerException e) {
				System.out.println("Specified path is empty.");
			} catch (FileNotFoundException e) {
				System.out.println("Specified file was not found.");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("No command line arguments provided.");
		}
	}
}
