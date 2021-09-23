package com.qa.pages.linkedin;

import com.qa.appium.AppiumHelper;
import com.qa.wrappers.CommonFunctions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage extends CommonFunctions {

    public SignInPage() {
        PageFactory.initElements(new AppiumFieldDecorator(AppiumHelper.driverInit()), this);
    }

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Email or Phone']")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement txtboxEmail;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Password']")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement txtboxPassword;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Continue']")
    @iOSXCUITFindBy(id = "NA")
    public MobileElement btnContinue;

    public void signIn(String userName,String password) {
        super.sendKeys(txtboxEmail,userName);
        super.sendKeys(txtboxPassword,password);
        super.click(btnContinue);
    }

}

