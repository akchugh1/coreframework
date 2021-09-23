package com.qa.StepDefinition;

import com.qa.constant.Constants;
import com.qa.context.Context;
import com.qa.context.TestContext;
import com.qa.controller.CoinMarketCapController;
import com.qa.controller.ControllerHelper;
import com.qa.rest.HttpResponse;
import com.qa.util.Reporter;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CryptoSteps {
    Properties config = Setup.config;
    HttpResponse response;
    TestContext testContext;
    CoinMarketCapController controller;
    ControllerHelper helper;
    Reporter reporter;

    public CryptoSteps(TestContext context) {
        testContext = context;
        controller = new CoinMarketCapController();
        helper = new ControllerHelper();
        reporter = new Reporter();
    }

    @When("user converts the above currency to {string} - {string} for amount {string}")
    public void user_converts_the_above_currency_to(String currencyName, String currencySymbol, String amount) {
        response = controller.priceConversion(config, currencySymbol, amount, (String) testContext.getScenarioContext().getContext(Context.CRYPTO_ID));
        Assert.assertEquals(Constants.SUCCESS_RESPONSE_CODE, response.getStatusCode());
    }

    @When("user retrieves the id of {string}")
    public void userRetrievesTheIdOf(String currency) {
        response = controller.getCryptocurrencyId(config, currency);
        Assert.assertEquals(Constants.SUCCESS_RESPONSE_CODE, response.getStatusCode());
        System.out.println(JsonPath.read(response.getBody(), "$.data[0].id").toString());
        testContext.getScenarioContext().setContext(Context.CRYPTO_ID, JsonPath.read(response.getBody(), "$.data[0].id").toString());
    }

    @Then("price of {string} is fetched successfully")
    public void priceOfIsFetchedSuccessfully(String currencySymbol) throws IOException {
        System.out.println(JsonPath.read(response.getBody(), "$.data.quote." + currencySymbol + ".price").toString());
        reporter.addAllureAttachment("Price", JsonPath.read(response.getBody(), "$.data.quote." + currencySymbol + ".price").toString());
    }

    @Given("user retrieves the technical documentation of {string}")
    public void userRetrievesTheTechnicalDocumentationOf(String currencyId) {
        response = controller.getCryptocurrencyInfo(config, currencyId);
        Assert.assertEquals(Constants.SUCCESS_RESPONSE_CODE, response.getStatusCode());
    }

    @And("user validates all the required information")
    public void userValidatesAllTheRequiredInformation(DataTable testData) {
        Map<String, String> expData = testData.asMaps().get(0);
        Map<String, String> actualData = helper.getCryptoInfoData(response.getBody(), expData.get("currencyId"));

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(Constants.LOGO.replace("<CURRENCY_ID>", expData.get("currencyId")), actualData.get("logo"));
        softAssert.assertEquals(expData.get("technicalDoc"),
                actualData.get("technicalDoc").replaceAll("\\[|\\]|\"", "").replace("\\", ""));
        softAssert.assertEquals(expData.get("dateAdded"), actualData.get("dateAdded"));
        softAssert.assertEquals(expData.get("currencySymbol"), actualData.get("symbol"));
        softAssert.assertTrue(Arrays.stream(actualData.get("tags").replaceAll("[^,a-zA-Z0-9]", "").split(",")).anyMatch(expData.get("tag")::equals));
        softAssert.assertAll();
    }

    @Given("user retrieves the technical documentation of first ten ids")
    public void userRetrievesTheTechnicalDocumentationOfFirstIds() {
        String currencyId = IntStream.rangeClosed(1, 10).mapToObj(String::valueOf).collect(Collectors.joining(","));
        response = controller.getCryptocurrencyInfo(config, currencyId);
        Assert.assertEquals(Constants.SUCCESS_RESPONSE_CODE, response.getStatusCode());

    }

    @Then("print the list of the currencies that have mineable tag")
    public void printTheListOfTheCurrenciesThatHaveMineableTag() {
        String currencyId = IntStream.rangeClosed(1, 10).mapToObj(String::valueOf).collect(Collectors.joining(","));
        Function<String, String> validate = (x) -> {
            if (Arrays.stream(JsonPath.read(response.getBody(), "$.data." + x + ".tags").toString().replaceAll("[^,a-zA-Z0-9]", "").split(",")).anyMatch("mineable"::equals)) {
                return x;
            }
            return "";
        };
        Arrays.stream(currencyId.split(",")).map(validate).filter(x -> x != "").collect(Collectors.toList()).stream().map(x ->JsonPath.read(response.getBody(), "$.data." + x + ".symbol").toString()).forEach(System.out::println);
    }
}
