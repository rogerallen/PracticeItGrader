package practiceItGrader;

import java.util.ArrayList;
import java.util.List;

public class Student {

	private String userName;
	private String lastName;
	private String firstName;
	private int numCorrect;
	private int numAttempted;
	private int numIncorrect;

	public Student(String studentName, String last, String first) {
		userName = studentName;
		lastName = last;
		firstName = first;
		numCorrect = 0;
		numAttempted = 0;
		numIncorrect = 0;
	}

	private List<Problem> problems = new ArrayList<Problem>();

	public void addProblem(Problem problem) {
		problems.add(problem);
	}

	public void printNumProblems() {
		System.out.println(userName + " did " + problems.size());

	}

	public void grade(List<String> assignments) {
		for (String curAssignment : assignments) {
			boolean triedProblem = false;
			for (Problem curProblem : problems) {
				if (curProblem.nameIs(curAssignment)) {
					triedProblem = true;
					numAttempted += 1;
					if (curProblem.isSolved()) {
						numCorrect += 1;
					} else {
						numIncorrect += 1;
					}
				}
			}
			if (!triedProblem) {
				numIncorrect += 1;
			}
		}
	}

	public int numCorrect() {
		return numCorrect;
	}

	public int numIncorrect() {
		return numIncorrect;
	}

	public int numAttempted() {
		return numAttempted;
	}

	public String userName() {
		return userName;
	}
	
	public String lastName() {
		return lastName;
	}

	public String firstName() {
		return firstName;
	}

}
