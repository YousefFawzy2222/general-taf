package com.blazedemo.utils;

import org.openqa.selenium.*;

import java.io.File;

public class ElementActions {
    private WebDriver driver;
    private final WaitManager waitBot;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitBot = new WaitManager(driver);

    }

    //Clicking
    public void click(By locator){
        waitBot
                .fluentWait()
                .until(d ->{ // lambda expression to click the element when it is ready to be clicked
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.click();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        ); //this until will return true if the element was clicked successfully within the 10 sec wait if not it returns false
    }

    //Typing
    public void type(By locator, String text){
        waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                );

    }

    //Getting Text
    public String getText(By locator){
        return waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator); // js scrolling -> force injection
                        String msg = element.getText();
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //Function to scroll to an element using js
    public void scrollToElementJS(By locator){
        ((JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"}});""", findElement(locator)); // scrolls to the element using js to be viewed in the viewport
    }

    //find an element
    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }

    //upload file
    public void uploadFile(By locator, String filePath){
        String fileAbsolute = System.getProperty("user.dir") + File.separator +filePath;
        waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

}
