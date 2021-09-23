package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.constant.Constants;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

public class AppLandingPage extends CommonFunctions {

    public AppLandingPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "com.linkedin.android:id/growth_prereg_carousel_item_image")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement swipingImage;

    @AndroidFindBy(id = "com.linkedin.android:id/growth_prereg_carousel_item_text")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement txtHomePage;

    @AndroidFindBy(id = "com.linkedin.android:id/growth_prereg_fragment_login_button")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnSignIn;

    public void validateTextOneachSwipe(){
        for(int counter = 0;counter<3;counter++){
            Assert.assertTrue(Constants.APP_LANDING_TEXT.contains(super.getText(txtHomePage)));
            super.horizontalSwipeElement(swipingImage);
        }
    }

    public void clickSignIn() {
        super.click(btnSignIn);
    }
}
