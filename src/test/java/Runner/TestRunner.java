package Runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources/com/qa/feature/"}, plugin = {"pretty",
        "json:target/cucumber_reports/Cucumber.json", "rerun:target/rerun.txt", // Creates a text file with failed
        // scenarios
        "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"}, glue = {
        "com/qa/StepDefinition"}, tags = {"@E2E_TEST"}, dryRun = false, stepNotifications = true)


public class TestRunner {

}

// ORed : tags = {"@Smoke, @Regression"} To add condition for OR operator
// Anded : tags = {"@Smoke", "@Regression"} To add condition for AND operator
// Ignore Case : tags = {"~@Smoke", "@Regression"} To ignore a set of test cases
// use ~ operator