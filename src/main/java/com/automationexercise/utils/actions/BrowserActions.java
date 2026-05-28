package com.automationexercise.utils.actions;

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {
    private final WebDriver driver;
    public BrowserActions(WebDriver driver){
        this.driver = driver;
    }
    /**
     * Maximize Window
     */
    public void maximizeWindow(){
        driver.manage().window().maximize();
        LogsManager.info("Window maximized");
    }
    //this comment below will be viewed as a description for the method
    /**
     * Get Current URL
     */
    public String getCurrentURL(){
        String url = driver.getCurrentUrl();
        LogsManager.info("Current URL: " + url);
        return url;
    }
    /**
     * Navigate to URL
     */
    public void navigateTo(String url){
        driver.get(url);
        LogsManager.info("Navigated to URL: " + url);
    }
    /**
     * Refresh Current window
     */
    public void refreshPage(){
        driver.navigate().refresh();
        LogsManager.info("Page refreshed");
    }
    /**
     * Close Current window
     */
    public void closeCurrentWindow(){
        driver.close();
        LogsManager.info("Current window closed");
    }
    /**
     * Open a New window
     */
    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
        LogsManager.info("New window opened");
    }
}
