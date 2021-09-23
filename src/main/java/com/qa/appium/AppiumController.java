package com.qa.appium;

import com.qa.logger.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class AppiumController {

	public static final AppiumController instance = new AppiumController();

	private AppiumController() {
	}
	public AppiumDriver<MobileElement> driver;

	public void appLaunch(Properties config) throws MalformedURLException {
		if (driver != null) {
			return;
		}
		DesiredCapabilities capabilities = new DesiredCapabilities();

		// defining directory of the path where app is present
		String appUrl = System.getProperty("user.dir") + config.getProperty("APP_URL");
		File appDir = new File(appUrl);
		File app = new File(appDir, config.getProperty("APP_NAME"));

		// defining appium url
		String appiumUrl = "http://" + config.getProperty("APPIUM_IP") + ":" + config.getProperty("APPIUM_PORT")
				+ "/wd/hub";

		String testName = config.getProperty("DEVICE_NAME") + System.currentTimeMillis();
		Log.info("$$$$$$ " + testName);

		switch (config.getProperty("OS")) {

		default:
			System.out.println("OS version is not correct");
			break;

			//tested and developed onlu on android emulator
		case "ANDROID":
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("PLATFORM_VERSION"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("DEVICE_NAME"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, config.getProperty("AUTOMATION_NAME"));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.getProperty("APP_PACKAGE"));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, config.getProperty("APP_ACTIVITY"));
			capabilities.setCapability(MobileCapabilityType.UDID, config.getProperty("DEVICE_ID"));
			capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, config.getProperty("SESSION_TIMEOUT"));
			capabilities.setCapability("newSessionWaitTimeout", "1200");
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			driver = new AndroidDriver<MobileElement>(new URL(appiumUrl), capabilities);
			break;

			//not tested and developed for ios
		case "IOS":
			capabilities.setCapability("testName", testName);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, config.getProperty("PLATFORM_VERSION"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, config.getProperty("DEVICE_NAME"));
			capabilities.setCapability(MobileCapabilityType.UDID, config.getProperty("DEVICE_ID"));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, config.getProperty("APP_PACKAGE"));
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, config.getProperty("APP_ACTIVITY"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, config.getProperty("AUTOMATION_NAME_IOS"));
			capabilities.setCapability(IOSMobileCapabilityType.SIMPLE_ISVISIBLE_CHECK, true);
			capabilities.setCapability(IOSMobileCapabilityType.PREVENT_WDAATTACHMENTS, true);
			capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, config.getProperty("SESSION_TIMEOUT"));
			capabilities.setCapability("newSessionWaitTimeout", "1200");
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
			driver = new IOSDriver<MobileElement>(new URL(appiumUrl), capabilities);
			break;
		}
	}

	public void appClose() {
		try {
				driver.closeApp();
				driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver = null;
		}
	}

	public void uninstallApplication(String packageName) {
		try {
			if (driver != null) {
				driver.removeApp(packageName);
			}
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver = null;
		}
	}

}
