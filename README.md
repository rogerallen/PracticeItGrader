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
## Building

I just used Eclipse and added the [commons-cli (v1.4)](https://www.apache.org/dist/commons/cli/binaries/) and [commons-csv (v1.5)](https://www.apache.org/dist/commons/csv/binaries/) JARs as Referenced Libraries.

## License

<p xmlns:dct="http://purl.org/dc/terms/" xmlns:vcard="http://www.w3.org/2001/vcard-rdf/3.0#">
  <a rel="license"
     href="http://creativecommons.org/publicdomain/zero/1.0/">
    <img src="http://i.creativecommons.org/p/zero/1.0/88x31.png" style="border-style: none;" alt="CC0" />
  </a>
  <br />
  To the extent possible under law,
  <a rel="dct:publisher"
     href="https://github.com/rogerallen/PracticeItGrader">
    <span property="dct:title">Roger Allen</span></a>
  has waived all copyright and related or neighboring rights to
  <span property="dct:title">PracticeIt Grader</span>.
This work is published from:
<span property="vcard:Country" datatype="dct:ISO3166"
      content="US" about="https://github.com/rogerallen/PracticeItGrader">
  United States</span>.
</p>
