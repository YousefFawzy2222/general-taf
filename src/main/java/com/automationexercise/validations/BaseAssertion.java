package com.automationexercise.validations;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Parent
public abstract class BaseAssertion {
    protected final WebDriver driver;
    protected final WaitManager waitManager;
    protected ElementActions elementActions;

    protected BaseAssertion(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected  abstract void assertEquals(Object actual, Object expected, String message);

    public void Equals(String actual, String expected, String message){
        assertEquals(actual, expected, message);
    }
    protected void isElementVisible(By locator){
        boolean flag = waitManager.fluentWait().until(driver1 ->{
            try{
                driver1.findElement(locator).isDisplayed();
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
        assertTrue(flag, "Element is not visible: " + locator);
    }
    //verify page url
    public void assertPageUrl(String expectedUrl){
        String actualUrl = driver.getCurrentUrl();
        assertEquals(actualUrl, expectedUrl, "Page URL mismatch. Expected: " + expectedUrl + ", Actual: " + actualUrl);
    }
    //verify element is visible
    public void isElementNotVisible(By locator){
        boolean flag = waitManager.fluentWait().until(driver1 ->{
            try{
                driver1.findElement(locator).isDisplayed();
                return false;
            }
            catch (Exception e){
                return true;
            }
        });
        assertTrue(flag, "Element is visible: " + locator);
    }
    //verify page title
    public void assertPageTitle(String expectedTitle){
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }
}
