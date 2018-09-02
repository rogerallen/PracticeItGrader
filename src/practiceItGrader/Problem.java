package practiceItGrader;

public class Problem {
	public Problem(String _name, String _solved, String _dateTime, String _tries) {
		name = _name;
		solved = _solved.equals("Yes");
		dateTime = _dateTime;
		tries = Integer.parseInt(_tries);
	}
	private String name;
	private boolean solved;
	private String dateTime;
	private int tries;
	public boolean nameIs(String curAssignment) {
		return name.equals(curAssignment);
	}
	public boolean isSolved() {
		return solved;
	}
}
