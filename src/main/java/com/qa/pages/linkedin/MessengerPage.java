package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class MessengerPage extends CommonFunctions {

    public MessengerPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(id = "com.linkedin.android:id/conversation_filter_lever_image")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnFilter;

    @AndroidFindBy(id = "com.linkedin.android:id/filter_connection_lever_btn")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement myConnections;

    public void filterByMyConnections(){
        super.click(btnFilter);
        super.click(myConnections);
    }


}
