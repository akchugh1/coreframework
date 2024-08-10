# Project Title

This project utilizes Cucumber with TestNG and Maven, and integrates Allure for reporting.

# Getting Started

Download the project & import the project in your IDE

mvn clean test will build the project


# Prerequisites for running tests using Cucumber TestNG Maven:

Environment Setup

# Java Installation

i.   For Windows :

You can download Java for Windows from [here](http://www.java.com/en/download/manual.jsp)

Run the installer and follow the setup wizard to install Java.

create a new JAVA_HOME environment variable and set variable value as path value to JDK folder.

#### This is Windows environment variable location :
   ```
   Control Panel > All Control Panel Items > System > Advanced system settings > Environment Variables
   ```

![altext](https://github.com/akchugh1/images/blob/main/Img1.png)

ii. For Linux :

use this command :
   ```
   sudo apt-get install openjdk-8-jre
   ```
iii. For Mac

Java should already be present on Mac OS X by default

### 2. Maven Installation

Install Maven from [here](https://maven.apache.org/install.html)

# Running Tests

To Run Your Test from command line, use following command :

    ```
    $ mvn clean install '-Dcucumber.options=--tags @E2E_TEST' -Drunner_name=TestRunner 
    ```

Run the tests by updating tag in the testrunner class
@ZOOM_TEST-->to run the zoom test case
@LINKEDIN_TEST-->To run linked in test case
@BACKEND_TEST-->to run all the backend tests
@E2E_TEST--> to run all backend test and nobile scenarios.

To run the tests in eclipse or intellij:
Goto TestRunner class-->Run as Junit test to run 

Here is the TestRunner file to automate our feature file using jUnit :
......

	package com.qa.Runner;

	import io.cucumber.testng.AbstractTestNGCucumberTests;
	import io.cucumber.junit.Cucumber;
	import io.cucumber.junit.CucumberOptions;

	@RunWith(Cucumber.class)
	@CucumberOptions(features = {"src/test/java/com/qa/feature/"}, plugin = {"pretty",
       "json:target/cucumber_reports/Cucumber.json", "rerun:target/rerun.txt", // Creates a text file with failed
        // scenarios
         "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"}, glue = {
            "com/qa/StepDefinition"}, tags = {"@E2E_TEST"}, dryRun = false, stepNotifications = true)
           
	public class TestRunner {

	}

.......

Here is the sample feature file for Cucumber :
.....

	Feature: Regression test of jqueryui web application
	Background: Browser is launched
	Given user launches the website

	Scenario: Verify user should be able Drag and Drop
	And user navigates to dropable from left menu
	Then user should be able to successfully drags and drop to the destination
	Then user closes the browser

.....

Step Definitions are in com.qa package

Main Java consists of below packages:

appium-->contains classes of appium driver initialisation
rest-->contains rest template client
pages->contain all classes for page object model
warppers-->contain classes of all the common function like wait condition, click,sendkeys etc(wrappers)
util-->consists of all utility functions
logger-->loggings functions
contect-->uncludes implementation of pico container
constants-->contains all constants

src/main/resources consists of config.properties for all the constants
environments.yml--> environments proerties bases on environments

......



Allure reports are implemented. 
to run the reports

run below command on terminal

	$ cd target
    $ allure serve allure-results


 Screenshot of the allure reports

![image](hhttps://github.com/akchugh1/images/blob/main/Screenshot1.png)

![image](hhttps://github.com/akchugh1/images/blob/main/Screenshot2.png)



	
