package com.automationexercise.drivers;

import com.automationexercise.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.List;

public abstract class AbstractDriver {

    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");
    protected List<File> extensions = List.of(
            new File("src/main/resources/extensions/PordaAI-Blur-Haram-objects-in-Images-and-Videos-SuperFast-Ai-for-Muslim-Chrome-Web-Store.crx"),
            new File("src/main/resources/extensions/AdGuard-AdBlocker.crx")
    );
    public abstract WebDriver createDriver();
}
