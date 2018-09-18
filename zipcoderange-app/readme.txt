zipcoderange-app
A simple application for optimizing ZIP code ranges to be used to determine if a ZIP code(s) should be allowed or excluded.

Requirements
BACKGROUND
Sometimes items cannot be shipped to certain zip codes, and the rules for these restrictions are stored as a series of ranges of 5 digit codes. For example if the ranges are:

[94133,94133] [94200,94299] [94600,94699]
Then the item can be shipped to zip code 94199, 94300, and 65532, but cannot be shipped to 94133, 94650, 94230, 94600, or 94299.

Any item might be restricted based on multiple sets of these ranges obtained from multiple sources.

PROBLEM
Given a collection of 5-digit ZIP code ranges (each range includes both their upper and lower bounds), provide an algorithm that produces the minimum number of ranges required to represent the same restrictions as the input.

NOTES
The ranges above are just examples, your implementation should work for any set of arbitrary ranges
Ranges may be provided in arbitrary order
Ranges may or may not overlap
Your solution will be evaluated on the correctness and the approach taken, and adherence to coding standards and best practices
EXAMPLES:
If the input = [94133,94133] [94200,94299] [94600,94699]

Then the output should be = [94133,94133] [94200,94299] [94600,94699]

If the input = [94133,94133] [94200,94299] [94226,94399]

Then the output should be = [94133,94133] [94200,94399]

Getting Started
After copying this maven project or jar, any further setup is completely optional. It is possible to use this application immediately if your environment if configured to use the tools listed below in Prerequisites.

Prerequisites
Your environment will need to be setup to use Java (JDK 8) and Maven.

Building
Maven is used for building the source code, generating the documentation, executing unit tests, and running the application. 

Run all commands from within the main project folder.
Maven tasks
The following Maven tasks are available:

build
clean
run
test
build
The build task will compile all Java classes (including unit tests) and copy the compiled classes and resource file(s) to the build folder.

To execute this, run: ZipCodeRangeApp.java with or without Optional ZipCode values to be checked for exclusion

When the ZipCodeApp class is executed, it will:

read the contents of build/resources/main/inputzipcoderangesdata.txt
To change the ZIP code range definitions, edit the file: src/main/resources/inputzipcoderangesdata.txt

After editing inputzipcoderangesdata.txt, it is necessary to run the build again.
process all of the ranges contained within inputzipcoderangesdata.txt
sort and consolidate all ranges into the minimum number of ranges
display both the original and consolidated ranges
optional: Any ZIP codes passed in via the command-line will be checked if they are in any of the excluded ranges
To add ZIP codes to the command-line, run java program ZipCodeRangeApp.java with arguments 95630 95667

test
The test task will execute the unit tests copy the results to the build/________________________ folder.

To execute this, run: mvn test

The unit test report can be seen by opening /target/surefire-reports/TEST-com.abc.app.zipcoderange.test.utils.ZipCodeRangeUtilsTest.xml with a web browser.

Libraries
This project uses the following libraries:

JUnit 4.11 - Unit testing framework
Hamcrest 2.0.0.0 - Framework used by JUnit for supporting matcher objects
Maven 4.0 - Build system
This project was developed using the Eclipse photon IDE.