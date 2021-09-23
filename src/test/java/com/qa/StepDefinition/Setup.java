package com.qa.StepDefinition;

import com.qa.appium.AppiumController;
import com.qa.appium.AppiumHelper;
import com.qa.config.Config;
import com.qa.logger.Log;
import com.qa.wrappers.CommonFunctions;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Setup {
    static Properties config;
    public static CommonFunctions com_fun = null;
    public static String directoryPath = null;
    static Scenario scenario = null;
    public static int screenshotCount = 1;
    public static boolean uninstallRequired = true;

    @Before
    public void setUp(Scenario scenario) {
        String appName;
        Log.startTestCase(scenario.getName());
        this.scenario = scenario;
        config = new Config().initializeConfig();
        // initiate driver
        if (!scenario.getName().contains("API")) {
            if (scenario.getName().contains("ZOOM") ) {
                config.setProperty("APP_NAME", config.getProperty("APP_NAME_ZOOM"));
                config.setProperty("APP_PACKAGE", config.getProperty("APP_PACKAGE_ZOOM"));
                config.setProperty("APP_ACTIVITY", config.getProperty("APP_ACTIVITY_ZOOM"));
            } else {
                config.setProperty("APP_NAME", config.getProperty("APP_NAME_LINKEDIN"));
                config.setProperty("APP_PACKAGE", config.getProperty("APP_PACKAGE_LINKEDIN"));
                config.setProperty("APP_ACTIVITY", config.getProperty("APP_ACTIVITY_LINKEDIN"));
            }
            // initiate driver
            Log.info("Launching Application");
            try {
                AppiumController.instance.appLaunch(config);
                AppiumHelper.driverInit().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            // Initialize Common Function
            com_fun = new CommonFunctions(AppiumHelper.driverInit());
            directoryPath = com_fun.directoryCreation(config);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        Log.endTestCase();

        if (scenario.isFailed()) {
            System.out.println("Test Case failed: " + scenario.getName());
            Log.info("Test Case failed: " + scenario.getName());
            uninstallRequired = true;
        } else {
            System.out.println("Test Case passed: " + scenario.getName());
        }
        if (!scenario.getName().contains("API")) {
            if (uninstallRequired == true) {
                AppiumController.instance.uninstallApplication(config.getProperty("APP_PACKAGE"));
            } else {
                AppiumController.instance.appClose();
            }
        }
    }

    @AfterStep
    public void screenShotAfterEachStep(Scenario s) {
        try {
            if (!scenario.getName().contains("API")) {
                this.embedScreeshot(String.valueOf(screenshotCount), s, directoryPath);
                screenshotCount++;
            }
        } catch (Exception e) {
            System.out.println("Unable to take the screenshot.");
        }
    }

    @SuppressWarnings("deprecation")
    public String embedScreeshot(String imageName, Scenario sc, String directoryPath) {
        String path = null;
        try {
            path = com_fun.takeScreenshots(directoryPath, imageName);
            File scrFile = new File(path);
            byte[] data = FileUtils.readFileToByteArray(scrFile);
            sc.embed(data, "image/png");
            sc.write(imageName);
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return path;
    }

    public static String getProperty(String propertyName) {
        return config.getProperty(propertyName);
    }

}
