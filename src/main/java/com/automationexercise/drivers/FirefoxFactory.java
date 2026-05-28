package com.automationexercise.drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxFactory extends AbstractDriver {
    private FirefoxOptions getOptions(){
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--remote-allow-origis=*");
        // Allows remote origins (used to bypass ChromeDriver security restrictions in newer Chrome versions)

        options.addArguments("--start-maximized");
        // Opens the browser in maximized window mode

        options.addArguments("--disable-notifications");
        // Disables browser notifications (prevents popups asking for permission)

        options.addArguments("--disable-popup-blocking");
        //  Disables the browser's popup blocking feature (allows popups to appear)

        options.addArguments("--disable-infobars");
        // Removes "Chrome is being controlled by automated test software" info bar

        options.addArguments("--disable-extensions");
        // Disables all installed Chrome extensions (clean test environment)

        options.addArguments("--disable-gpu");
        // Disables GPU hardware acceleration (useful for stability in some environments, especially CI)

        options.setAcceptInsecureCerts(true);
        // Accepts SSL certificates even if they are invalid/self-signed (useful for test environments)

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // Tells Selenium to continue once DOM is loaded, without waiting for all resources (faster tests)

        return options;
    }
    @Override
    public WebDriver createDriver() {
        return new FirefoxDriver(getOptions()); //Creates an object from chrome web driver and intializes its options with getOptions
    }
}
