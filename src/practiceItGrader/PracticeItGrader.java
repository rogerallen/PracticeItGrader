package practiceItGrader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
// https://www.callicoder.com/java-read-write-csv-file-apache-commons-csv/
// commons-csv 
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PracticeItGrader {

	private static HashMap<String, Student> students;
	private static List<String> assignments;

	public static void main(String[] args) throws IOException {

		CommandLine cmd = getCommandlineOptions(args);

		String pifileName = cmd.getOptionValue("practiceItCsv");
		String assignmentName = cmd.getOptionValue("assignmentCsv");
		String outfileName = cmd.getOptionValue("outputCsv");

		students = new HashMap<String, Student>();
		assignments = new ArrayList<String>();

		readInPracticeItCsv(pifileName);
		readInAssignmentCsv(assignmentName);
		gradeAssignment();
		writeOutGradeCsv(outfileName);

	}

	private static CommandLine getCommandlineOptions(String[] args) {
		Options options = new Options();

		Option pifileNameOption = new Option("p", "practiceItCsv", true,
				"practiceit full course exported CSV file path");
		pifileNameOption.setRequired(true);
		options.addOption(pifileNameOption);

		Option assignmentNameOption = new Option("a", "assignmentCsv", true,
				"assignment with problem names CSV file path");
		assignmentNameOption.setRequired(true);
		options.addOption(assignmentNameOption);

		Option outputFileOption = new Option("o", "outputCsv", true, "student grades output CSV file path");
		outputFileOption.setRequired(true);
		options.addOption(outputFileOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("grader", options);
			System.exit(1);
		}
		return cmd;
	}

	private static void gradeAssignment() {
		for (Object student : students.values()) {
			((Student) student).grade(assignments);
		}
	}

	private static void readInPracticeItCsv(String fileName) throws IOException {
		try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
						// .withIgnoreHeaderCase()
						.withTrim());) {
			// "Username","Last","First","Problem","Solved?","Date/Time","Tries","Solution
			// Code"
			for (CSVRecord csvRecord : csvParser) {
				String studentName = csvRecord.get("Username");
				if (!students.containsKey(studentName)) {
					// add new student
					students.put(studentName, new Student(studentName, csvRecord.get("Last"), csvRecord.get("First")));
				}
				students.get(studentName).addProblem(new Problem(csvRecord.get("Problem"), csvRecord.get("Solved?"),
						csvRecord.get("Date/Time"), csvRecord.get("Tries")));
			}
			System.out.println("# Students = " + students.size());
		}
	}

	private static void readInAssignmentCsv(String fileName) throws IOException {
		try (Reader reader = Files.newBufferedReader(Paths.get(fileName));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
						// .withFirstRecordAsHeader()
						// .withIgnoreHeaderCase()
						.withTrim());) {
			// "Assignment"
			for (CSVRecord csvRecord : csvParser) {
				assignments.add(csvRecord.get(0));
			}
			System.out.println("# Assignments = " + assignments.size());
		}
	}

	private static void writeOutGradeCsv(String fileName) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("FirstName", "LastName",
						"NumCorrect", "NumIncorrect", "NumAttempted"));) {
			System.out.println("Outputting file: " + fileName);
			for (Object student : students.values()) {
				csvPrinter.printRecord(((Student) student).firstName(), ((Student) student).lastName(),
						((Student) student).numCorrect(), ((Student) student).numIncorrect(),
						((Student) student).numAttempted());
			}
			csvPrinter.flush();
		}

	}

}
