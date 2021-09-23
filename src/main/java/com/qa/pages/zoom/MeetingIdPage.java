package com.qa.pages.zoom;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class MeetingIdPage extends CommonFunctions {

    public MeetingIdPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "us.zoom.videomeetings:id/edtConfNumber")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement txtboxMeetingId;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/btnJoin")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnJoin;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/chkNoVideo")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnNoVideo;

    public void enterMeetingId(String meetingId) {
        super.sendKeys(txtboxMeetingId,meetingId);
    }

    public boolean isJoinMeetingButtonEnabled(){
        return super.isElementEnabled(btnJoin);
    }

    public void clickJoinMeetingButton(){
        super.click(btnJoin);
    }

    public void toggleNoVideoBtn(String toggleFlag){
        String validationFlag = "false";
        if (toggleFlag.equalsIgnoreCase("on")) {
            validationFlag = "true";
        }
        if (!btnNoVideo.getAttribute("checked").equalsIgnoreCase(validationFlag)) {
            super.click(btnNoVideo);
        }

    }
}
