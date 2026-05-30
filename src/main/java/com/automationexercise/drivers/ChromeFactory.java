package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class ChromeFactory extends AbstractDriver{
    private final String remoteHost = PropertyReader.getProperty("remoteHost");
    private final String remotePort = PropertyReader.getProperty("remotePort");
    private ChromeOptions getOptions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
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

        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
            options.addArguments("--headless");
        }

        return options;
    }
    @Override
    public WebDriver createDriver() {
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")){
            return new ChromeDriver(getOptions()); //Creates an object from chrome web driver and intializes its options with getOptions

        }else if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
            try{
                return new RemoteWebDriver(
                        new URI("http://" + remoteHost + ":" + remotePort + "/wd/hub").toURL(), getOptions()
                );
            }catch (Exception e) {
                LogsManager.error("Failed to create RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Failed to create RemoteWebDriver", e);
            }
        }
        else{
            LogsManager.error("Invalid execution type specified: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type specified: " + PropertyReader.getProperty("executionType"));
        }
    }
}
