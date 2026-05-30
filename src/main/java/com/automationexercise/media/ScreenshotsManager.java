package com.automationexercise.media;

import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttahcmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;

public class ScreenshotsManager {
    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    //take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName){
        try {
            // Capture screenshot using TakeScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-"+ TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttahcmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());

        } catch (IOException e) {
            LogsManager.error("Error taking screenshot: " + e.getMessage());
        }
    }

    //take screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector){
        try{
            String ariaName = driver.findElement(elementSelector).getAccessibleName(); // Accessible name is the name f the element on the page
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-"+ TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttahcmentManager.attachScreenshot(ariaName, screenshotFile.getAbsolutePath());

        }catch (IOException e){
            LogsManager.error("Error taking element screenshot: " + e.getMessage());
        }
    }
}
