package com.automationexercise.drivers;

import com.automationexercise.utils.actions.AlertActions;
import com.automationexercise.utils.actions.BrowserActions;
import com.automationexercise.utils.actions.ElementActions;
import com.automationexercise.utils.actions.FrameActions;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.validations.Validation;
import com.automationexercise.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private final String browser = PropertyReader.getProperty("browserType");

    public GUIDriver() {
        LogsManager.info("Initializing GUIDriver with browser type: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Initializing driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions element(){
        return new ElementActions(get());
    }

    public BrowserActions browser(){
        return new BrowserActions(get());
    }

    public FrameActions frame(){
        return new FrameActions(get());
    }

    public AlertActions alert(){
        return new AlertActions(get());
    }

    //soft Assertion
    public Validation validation(){
        return new Validation(get());
    }

    //Hard Assertion
    public Verification verification(){
        return new Verification(get());
    }

    public WebDriver get(){
        return driverThreadLocal.get();
    }
    public void quitDriver(){
        driverThreadLocal.get().quit();
    }

}
