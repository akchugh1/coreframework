package com.qa.StepDefinition;

import com.qa.pages.linkedin.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LinkedInSteps {

    HomePage homePage;
    WelcomeChromePage welcomeChromePage;
    MessengerPage messengerPage;
    SettingsPage settingsPage;
    SignInPage signInPage;
    AppLandingPage appLandingPage;

    public LinkedInSteps(){
        homePage = new HomePage();
        welcomeChromePage = new WelcomeChromePage();
        messengerPage = new MessengerPage();
        settingsPage = new SettingsPage();
        signInPage = new SignInPage();
        appLandingPage = new AppLandingPage();
    }

    @Given("user validates the text on landing page")
    public void userValidatesTheTextOnLandingPage() throws InterruptedException {
        appLandingPage.validateTextOneachSwipe();
        appLandingPage.clickSignIn();
    }

    @Then("user tap on filter icon with my connections on Search Messages")
    public void userTapOnFilterIconWithMyConnectionsOnSearchMessages() {
        messengerPage.filterByMyConnections();
        homePage.clickBackBtn();
    }

    @When("user signs in with its credentials")
    public void userSignsInWithItsCredentials() {
        Setup.com_fun.driverWait(4000);
        signInPage.signIn(Setup.getProperty("USER_NAME"),Setup.getProperty("PASSWORD"));
    }

    @Then("user reaches the home page and searches {string} in search bar")
    public void userReachesTheHomePageAndSearchesInSearchBar(String searchText) {
        homePage.searchText(searchText);
    }

    @And("user validates all results have {string} in its search text")
    public void userValidatesAllResultsHaveInItsSearchText(String searchText) {
        homePage.validateSearchText(searchText);
    }

    @And("see all results button is displayed")
    public void seeAllResultsButtonIsDisplayed() {
        homePage.validateSeeAllResultsButton();
        homePage.clickBackBtn();
    }

    @When("user opens chat from home page")
    public void userOpensChatFromHomePage() {
        homePage.openChat();
    }

    @When("user tap on profile photo and settings button")
    public void userTapOnProfilePhotoAndSettingsButton() {
        homePage.clickProfilePhoto();
        settingsPage.gotoSettingsPage();
    }

    @Then("user scrolls down to the bottom till Linked in banner is displayed")
    public void userScrollsDownToTheBottomTillLinkedInBannerIsDisplayed() {
        settingsPage.goTillEndOfThePage();
    }

    @And("user reaches to sign in page")
    public void userReachesToSignInPage() {
        welcomeChromePage.navigateTillSignInPage();
    }
}
