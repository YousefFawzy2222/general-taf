package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class EdgeFactory extends AbstractDriver {
    private EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        // Allows remote origins (used to bypass ChromeDriver security restrictions in newer Chrome versions)

        options.addArguments("--start-maximized");
        // Opens the browser in maximized window mode

        Map<String,Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + "\\src\\test\\resources\\downloads";
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download",false);
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS, false);

        options.addArguments("--disable-notifications");
        // Disables browser notifications (prevents popups asking for permission)

        options.addArguments("--disable-popup-blocking");
        //  Disables the browser's popup blocking feature (allows popups to appear)

        options.addArguments("--disable-infobars");
        // Removes "Chrome is being controlled by automated test software" info bar

        options.setAcceptInsecureCerts(true);
        // Accepts SSL certificates even if they are invalid/self-signed (useful for test environments)

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        // Tells Selenium to continue once DOM is loaded, without waiting for all resources (faster tests)

        String executionType = PropertyReader.getProperty("executionType");

        if (executionType.equalsIgnoreCase("Local")) {
            options.addExtensions(extensions);
        }

        if (executionType.equalsIgnoreCase("LocalHeadless")) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-extensions");
        }
        return options;
    }

    @Override
    public WebDriver createDriver() {
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")){
            return new EdgeDriver(getOptions()); //Creates an object from chrome web driver and intializes its options with getOptions

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
