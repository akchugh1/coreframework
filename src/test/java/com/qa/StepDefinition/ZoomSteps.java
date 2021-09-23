package com.qa.StepDefinition;

import com.qa.pages.zoom.HomePage;
import com.qa.pages.zoom.MeetingIdPage;
import com.qa.pages.zoom.PrepMeetingPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;

public class ZoomSteps {

    HomePage homePage;
    MeetingIdPage meetingIdPage;
    PrepMeetingPage prepMeetingPage;

    public ZoomSteps(){
        homePage = new HomePage();
        meetingIdPage = new MeetingIdPage();
        prepMeetingPage = new PrepMeetingPage();
    }

    @Given("user clicks on join meeting")
    public void userClicksOnJoinMeeting() {
        homePage.clickJoinMeeting();
    }

    @When("user enters any {int} digits meeting id")
    public void userEntersAnyDigitsMeetingId(int meetingIdDigits) {
        meetingIdPage.enterMeetingId(RandomStringUtils.randomNumeric(meetingIdDigits));
    }

    @Then("user validates join button is {string}")
    public void userValidatesJoinButtonIs(String toggleFlag) {
        if (toggleFlag.equalsIgnoreCase("disabled")) {
            Assert.assertFalse("Join button is enabled",meetingIdPage.isJoinMeetingButtonEnabled());
        } else {
            Assert.assertTrue("Join button is disabled",meetingIdPage.isJoinMeetingButtonEnabled());
        }
    }

    @When("user toggles {string} Turn off my video button")
    public void userTogglesTurnOffMyVideoButton(String toggleFlag) {
        meetingIdPage.toggleNoVideoBtn(toggleFlag);
    }

    @And("user clicks on join button")
    public void userClicksOnJoinButton() {
        meetingIdPage.clickJoinMeetingButton();
    }

    @And("user relaunches the app after putting in background for {int} seconds")
    public void userRelaunchesTheAppAfterPuttingInBackgroundForSeconds(int timeInSeconds) {
        prepMeetingPage.waitCondition(prepMeetingPage.popupMsg);
        Setup.com_fun.putAppInBackground(timeInSeconds);
        Setup.com_fun.activateApp(Setup.getProperty("APP_PACKAGE_ZOOM"));
    }

    @Then("user validates the invalid meeting id in popup message")
    public void userValidatesTheInvalidMeetingIdInPopupMessage() {
        prepMeetingPage.waitCondition(prepMeetingPage.popupMsg);
        Assert.assertTrue("Popup msg is not correct", prepMeetingPage.validatePopupMsg());
        prepMeetingPage.clickOkButton();
    }
}
