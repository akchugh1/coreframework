package com.qa.pages.zoom;

import com.qa.appium.AppiumHelper;
import com.qa.constant.Constants;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class PrepMeetingPage extends CommonFunctions {

    public PrepMeetingPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "us.zoom.videomeetings:id/txtMsg")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement popupMsg;

    @AndroidFindBy(id = "us.zoom.videomeetings:id/button2")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnOk;

    public boolean validatePopupMsg() {
        return super.validateText(popupMsg, Constants.MEETING_POPUP_TXT);
    }

    public void clickOkButton() {
        super.click(btnOk);
    }
}
