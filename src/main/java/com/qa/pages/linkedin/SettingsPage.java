package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class SettingsPage extends CommonFunctions {

    public SettingsPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "com.linkedin.android:id/interests_panel_view_settings")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnSettings;

    @AndroidFindBy(id = "com.linkedin.android:id/settings_toolbar_help_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnHelp;

    @AndroidFindBy(xpath = "(//android.webkit.WebView[@text='Settings']/child::*/*[@class='android.view.View'])[2]")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement bannerLinkedin;

    public void gotoSettingsPage(){
        super.click(btnSettings);
        super.waitCondition(btnHelp);
    }

    public void goTillEndOfThePage(){
        super.genericVerticalScroll(bannerLinkedin, 5, 0.8, 0.3);
    }


}
