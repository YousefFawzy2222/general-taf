package com.blazedemo.utils;

import com.blazedemo.drivers.Browser;
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
    }
    //this comment below will be viewed as a description for the method
    /**
     * Get Current URL
     */
    public String getCurrentURL(){
        return driver.getCurrentUrl();
    }
    /**
     * Navigate to URL
     */
    public void navigateTo(String url){
        driver.get(url);
    }
    /**
     * Refresh Current window
     */
    public void refreshPage(){
        driver.navigate().refresh();
    }
    /**
     * Close Current window
     */
    public void closeCurrentWindow(){
        driver.close();
    }
    /**
     * Open a New window
     */
    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }
}
