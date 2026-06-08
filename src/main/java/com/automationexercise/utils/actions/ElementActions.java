package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private final WaitManager waitBot;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitBot = new WaitManager(driver);

    }

    //Clicking
    public ElementActions click(By locator){
        waitBot
                .fluentWait()
                .until(d ->{ // lambda expression to click the element when it is ready to be clicked
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        //Wait until the element is stable (not moving)
                        Point initialLocation =  element.getLocation();
                        LogsManager.info("Element location before waiting: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("Element Final Location: " + finalLocation);
                        if (!initialLocation.equals(finalLocation))
                            return false;
                        element.click();
                        LogsManager.info("Clicked on Element:" + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        ); //this until will return true if the element was clicked successfully within the 10 sec wait if not it returns false
        return this;
    }

    //Typing
    public ElementActions type(By locator, String text){
        waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into Element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                );
        return this;
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
                        LogsManager.info("Retried text'" + msg + "' from Element: " + locator);
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //Hovering
    public ElementActions hover(By locator){
        waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered on Element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //Function to scroll to an element using js
    public void scrollToElementJS(By locator){
        ((JavascriptExecutor) driver)
                .executeScript("""
                        arguments[0].scrollIntoView({behavior:"auto", block:"center", inline:"center"});""", findElement(locator)); // Scrolls the element into the center of the viewport using JavaScript
    }

    //find an element
    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }

    //upload file
    public ElementActions uploadFile(By locator, String filePath){
        String fileAbsolute = System.getProperty("user.dir") + File.separator +filePath;
        waitBot
                .fluentWait()
                .until(d ->{
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file '" + fileAbsolute + "' to Element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //select from dropdown
    public ElementActions selectFromDropDown (By locator, String value){
        waitBot.fluentWait().until(d ->{
           try {
               WebElement dropdown = d.findElement(locator);
               scrollToElementJS(locator);
               Select select = new Select(dropdown);
               select.selectByVisibleText(value);
               LogsManager.info("Selected value '" + value + "' from dropdown: " + locator);
               return true;
           }catch (Exception e){
               return false;
           }
        });
        return this;
    }
}
