package com.qa.wrappers;

import com.qa.appium.AppiumHelper;
import com.qa.logger.Log;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonFunctions {

    AppiumDriver<MobileElement> driver;

    public CommonFunctions() {
        this.driver = AppiumHelper.driverInit();
    }

    public CommonFunctions(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
    }

    /**
     * Method to wait till particular timeout
     * 
     * @param ae
     *            Mobile Element Object
     * @paramdriver
     */
    public void waitCondition(MobileElement ae, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(ae));
    }

    /**
     * Method to hide active keyboard.
     *
     */
    public void hideKeyBoard() {
        if (driver instanceof AndroidDriver<?>) {
            ((AndroidDriver<MobileElement>) driver).isKeyboardShown();
            driver.hideKeyboard();
        }
    }

    /**
     * Method to clear and Type.
     *
     * @param: Element
     *             for the text field
     * @param: Text
     *             to enter
     */
    public void sendKeys(MobileElement ae, String text) {
        waitCondition(ae);
        ae.clear();
        ae.sendKeys(text);
        try {
            hideKeyBoard();
        } catch (Exception e) {
            Log.info("No Hiding Keyboard");
        }
    }

    /**
     * Method to wait the tread.
     *
     * @param duration
     *            wait time in long
     */
    public void driverWait(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // Log.info("Thread.wait failed to execute");
        }
    }

    /**
     * Method to wait for visibility of element
     *
     * @paramae
     *            Android element to wait for
     */

    public void waitConditionForListOfMobileElements(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath))).stream()
                .map(element -> (MobileElement) element).collect(Collectors.toList());
    }

    public void waitCondition(MobileElement ae) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOf(ae));
        } catch (TimeoutException e) {
            genericVerticalScroll(ae, 6, 0.5, 0.2);
        }
    }

    @SuppressWarnings("rawtypes")
    public void genericVerticalSwipe(double startyRatio) {
        Dimension dim = driver.manage().window().getSize();
        int xval = dim.getWidth() / 2;
        int starty = (int) (dim.getHeight() * startyRatio);
        int endy = (int) (dim.getHeight() * 0.20);
        new TouchAction(driver).press(ElementOption.point(xval, starty))
                .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                .moveTo(ElementOption.point(xval, endy)).release().perform();
    }

    public void embedScreenshot() {
        byte[] screenShot = ((TakesScreenshot) AppiumHelper.driverInit()).getScreenshotAs(OutputType.BYTES);
        Allure.getLifecycle().addAttachment(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yy_hh:mm:ss")), "image/png", "png",
                screenShot);
    }

    public MobileElement findElementByXpath(String xpath) {
        int counter = 1;
        MobileElement ie = null;
        do {
            try {
                ie = driver.findElementByXPath(xpath);
            } catch (Exception e) {
                ie = null;
                counter++;
                driverWait(1000);
            }
        } while (ie == null && counter <= 15);
        return ie;
    }

    public MobileElement findElementByString(String text, String type) {
        int counter = 1;
        MobileElement ie = null;
        do {
            try {
                String xpath = String.format("//%s[@name='%s']", type, text);
                ie = driver.findElementByXPath(xpath);
            } catch (Exception e) {
                ie = null;
                counter++;
                driverWait(1000);
            }
        } while (ie == null && counter <= 15);
        return ie;
    }

    /**
     * Method to valdate text.
     *
     * @param: Element
     *             for the text field
     * @param: Text
     *             to verify
     */
    public boolean validateText(MobileElement ae, String text) {
        waitCondition(ae);
        System.out.println("Expected Text = " + text.replace("  ", " "));
        System.out.println("Actual Text = " + ae.getText().replace("\n", "").replace("  ", " "));
        if (ae.getText().replace("\n", "").replace("  ", " ").toUpperCase()
                .equals(text.toUpperCase().replace("  ", " "))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to minimize the application on screen using Home button.
     *
     */
    public void minimizeApplication() {
        ((PressesKey) driver).pressKey(new io.appium.java_client.android.nativekey.KeyEvent(AndroidKey.HOME));
        // driver.pressKeyCode(AndroidKeyCode.HOME);
        driverWait(2000);

        // driver.pressKey(new KeyEvent(AndroidKey.HOME));
        // driverWait(2000);
    }

    public void relaunchApp(int timeInSeconds) {
        driver.runAppInBackground(Duration.ofSeconds(timeInSeconds));
        driver.launchApp();
    }

    public void putAppInBackground(int timeInSeconds) {
        driver.runAppInBackground(Duration.ofSeconds(timeInSeconds));
    }

    public void activateApp(String packageName) {
        driver.activateApp(packageName);
    }

    @SuppressWarnings("rawtypes")
    public void genericVerticalScroll(MobileElement element, int loopCount, double startyRatio, double endyRatio) {
        for (int i = 1; i < loopCount; i++) {
            try {
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Dimension dim = driver.manage().window().getSize();
            int xval = dim.getWidth() / 2;
            int starty = (int) (dim.getHeight() * startyRatio);
            int endy = (int) (dim.getHeight() * endyRatio);

            new TouchAction(driver).press(ElementOption.point(xval, starty))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(ElementOption.point(xval, endy)).release().perform();
        }
    }

    /**
     * Method to Swipe by elements.
     *
     * @param: startElement
     *             start element to swipe
     * @param: endElement
     *             end element to swipe
     */
    //// Swipe by elements from start to end point
    @SuppressWarnings("rawtypes")
    public void genericHorizontalSwipeByElements(MobileElement end, MobileElement start) {
        int startX = end.getLocation().getX();
        int startY = end.getLocation().getY();
        int endX = start.getLocation().getX() + 270;
        int endY = start.getLocation().getY();
        new TouchAction(driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    public String takeScreenshots(String dirPath, String imageName) {
        try {
            dirPath = dirPath + imageName + ".png";
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(dirPath));
        } catch (IOException e) {
            Log.error(e.getMessage());
        }
        return dirPath;
    }

    public String directoryCreation(Properties prop) {

        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MMM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        Calendar now = Calendar.getInstance();
        String dirPath = null;

        try {
            // use System.getProperty in directory path
            dirPath = System.getProperty("user.dir") + prop.getProperty("SCREENSHOT_PATH") + "/"
                    + prop.getProperty("OS") + "/" + day.format(now.getTime()) + "-" + month.format(now.getTime()) + "-"
                    + year.format(now.getTime()) + "/";
            if (dirPath.contains("@")) {
                dirPath = dirPath.replace("@", "");
            }
            File theDir = new File(dirPath);
            if (!theDir.exists()) {
                boolean result = false;
                try {
                    if (theDir.mkdirs()) {
                        result = true;
                    }
                } catch (SecurityException localSecurityException) {
                    return dirPath;
                }
                if (result) {
                    System.out.println("False");
                }
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return dirPath;
    }

    public void toggleCheckBox(MobileElement ae, String state) {
        switch (state) {
            case "ENABLE": {
                if (!ae.isSelected()) {
                    ae.click();
                }
                break;
            }
            case "DISABLE": {
                if (ae.isSelected()) {
                    ae.click();
                }
                break;
            }
            default:
                break;
        }
    }

    public void click(MobileElement ae) {
        waitCondition(ae);
        waitForElementToEnable(ae);
        ae.click();
    }

    public void switchContext(String expContext) {
        Set<String> contextNames = driver.getContextHandles();
        for (String contextName:contextNames) {
            System.out.println(contextName);
            if (contextName.contains(expContext)) {
                driver.context(expContext);
                driver.findElement(By.id("close_btn")).click();
            }
        }
    }

    /**
     * Method to Swipe element left.
     *
     * @param: element
     *             element to swipe
     * @param: endElement
     *             end element to swipe
     */
    //// Swipe by elements from left to right
    public void horizontalSwipeElement(MobileElement element) {
        int startX = element.getLocation().getX() + 370;
        int startY = element.getLocation().getY();
        int endX = element.getLocation().getX();
        int endY = element.getLocation().getY();
        new TouchAction(driver).press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    public String getText(MobileElement ae) {
        waitCondition(ae);
        return ae.getText();
    }

    public void waitForElementToEnable(MobileElement ae) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(ae));
        } catch (TimeoutException e) {
            genericVerticalScroll(ae, 6, 0.5, 0.2);
        }
        if (!ae.isEnabled()) {
            throw new RuntimeException("Element is not enabled yet.");
        }
    }

    public boolean isElementEnabled(MobileElement ae) {
        if (ae.isEnabled()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementPresent(MobileElement ae, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(ae));
        } catch (Exception e) {
            return false;
        }
        return ae.isDisplayed();
    }

}
