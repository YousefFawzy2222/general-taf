package com.blazedemo.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import utils.PropertyReader;

public class GUIDriver {
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String browser = PropertyReader.getProperty("browserType");

    public GUIDriver() {
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }
    public WebDriver getCurrentDriver(){
        return driverThreadLocal.get();
    }
    public void quitDriver(){
        driverThreadLocal.get().quit();
    }

}
