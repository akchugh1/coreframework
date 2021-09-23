package com.qa.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public abstract class AppiumHelper {

    public static AppiumDriver<MobileElement> driverInit() {
        return AppiumController.instance.driver;
    }

    public static void removeDriver(){
        AppiumController.instance.driver=null;
    }

}
