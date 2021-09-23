package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomeChromePage extends CommonFunctions {

    public WelcomeChromePage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }
    
    @AndroidFindBy(id = "com.android.chrome:id/terms_accept")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnAccept;

    @AndroidFindBy(id = "com.android.chrome:id/next_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnNext;

    @AndroidFindBy(id = "com.android.chrome:id/negative_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnNoThanks;

    public void navigateTillSignInPage() {
        super.click(btnAccept);
        super.click(btnNext);
        super.click(btnNoThanks);
    }
}
