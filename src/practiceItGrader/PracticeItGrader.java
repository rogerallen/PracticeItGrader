package practiceItGrader;

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
		String pifileName = args[0];
		String assignmentName = args[1];
		String outfileName = args[2];

		students = new HashMap<String, Student>();
		assignments = new ArrayList<String>();

		readInPracticeItCsv(pifileName);
		readInAssignmentCsv(assignmentName);
		gradeAssignment();
		writeOutGradeCsv(outfileName);

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

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("LastName", "FirstName",
						"NumCorrect", "NumIncorrect", "NumAttempted"));) {
			System.out.println("Outputting file: " + fileName);
			for (Object student : students.values()) {
				csvPrinter.printRecord(((Student) student).lastName(), ((Student) student).firstName(),
						((Student) student).numCorrect(), ((Student) student).numIncorrect(),
						((Student) student).numAttempted());
			}
			csvPrinter.flush();
		}

	}

}
