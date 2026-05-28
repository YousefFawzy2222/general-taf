package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String browser = PropertyReader.getProperty("browserType");

    public GUIDriver() {
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Initializing driver for browser: " + browserType);
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
