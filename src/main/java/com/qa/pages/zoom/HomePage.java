package com.qa.pages.zoom;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends CommonFunctions {

    public HomePage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "us.zoom.videomeetings:id/btnJoinConf")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement joinMeeting;

    public void clickJoinMeeting() {
        super.click(joinMeeting);
    }
}
