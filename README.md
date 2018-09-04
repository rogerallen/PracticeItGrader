# PracticeItGrader

Code to help grade https://practiceit.cs.washington.edu/ student results.

## Usage

1. Download Full results for your Course & Category (Select 'include attempts') from https://practiceit.cs.washington.edu/teacher/student-results 

2. Create an assignments file with one line per problem.  These entries must match the "Problem" column from step 1. E.g.

```
BJP4 Self-Check 1.06: legalIdentifiers
BJP4 Self-Check 1.07: outputSyntax
BJP4 Self-Check 1.08: confounding
```

3. Run this program to output a results file, one line per student. E.g.

`java grades.jar java -jar target/grader.jar -p practice-it-rogerallen-results-2018-09-02.csv -a assignment.csv -o output.csv`

The `output.csv` file will have a format like this:

```
LastName,FirstName,NumCorrect,NumIncorrect,NumAttempted
Abel,Zed,3,5,3
Baker,Aleph,4,4,4
Charlie,Parker,4,4,4
Duck,Bill,8,0,8
```

